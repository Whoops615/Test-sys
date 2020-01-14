package by.htp.ts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.htp.ts.bean.Answer;
import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.TestInfo;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.TestDAO;
import by.htp.ts.dao.connect_pool.ConnectionPool;

public class SQLTestDAO implements TestDAO {

	private static Lock lock = new ReentrantLock();

	private String SQL_INSERT_TEST = "INSERT INTO tests (title, time) VALUES (?,?)";
	private String SQL_SELECT_TEST = "SELECT id, title FROM tests WHERE title = ?;";
	private String SQL_ID = "SET @id = LAST_INSERT_ID();";
	private String INSERT_QUESTION_TEXT = "INSERT INTO test_questions (text, tests_id) VALUE (?,?)";
	private String INSERT_ANSWER = "INSERT INTO test_answer (test_answer_variance, isRight, test_questions_id)\r\n"
			+ "VALUES (?, ?, @id);";

	@Override
	public boolean saveTest(String title, LocalTime loc) throws DAOException {

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection con = cp.takeConnection();
		PreparedStatement ps = null;

		try {

			lock.lock();

			if (checkTitleTest(title) != 0) {
				return false;
			}

			ps = con.prepareStatement(SQL_INSERT_TEST);

			ps.setString(1, title);
			Time time = Time.valueOf(loc);
			ps.setTime(2, time);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);

		} finally {

			lock.unlock();
			cp.closeConnection(con, ps);

		}

		return true;
	}

	private int checkTitleTest(String title) throws DAOException {

		// передать сюда коннект, что бы не брать 2 коннекта!

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection con = cp.takeConnection();
		ResultSet rs = null;
		PreparedStatement ps1 = null;

		try {
			ps1 = con.prepareStatement(SQL_SELECT_TEST);
			ps1.setString(1, title);
			rs = ps1.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			cp.closeConnection(con, ps1, rs);
		}

		return 0;
	}

	@Override
	public boolean saveQuestion(String title, Question question) throws DAOException {

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection con = cp.takeConnection();

		PreparedStatement ps1 = null;
		Statement st = null;
		PreparedStatement ps2 = null;

		int testId = checkTitleTest(title);

		if (testId == 0) {
			return false;
		}

		try {

			ps1 = con.prepareStatement(INSERT_QUESTION_TEXT);
			ps1.setString(1, question.getContent());
			ps1.setInt(2, testId);
			ps1.executeUpdate();

			st = con.createStatement();
			st.executeUpdate(SQL_ID);

			ps2 = con.prepareStatement(INSERT_ANSWER);

			for (Answer a : question.getAnswers()) {

				ps2.setString(1, a.getContent());

				if (a.isRightness()) {
					ps2.setInt(2, 1);
				} else {
					ps2.setInt(2, 0);
				}

				ps2.addBatch();

			}

			ps2.executeBatch();

		} catch (SQLException e) {
			throw new DAOException(e);

		} finally {
			if (ps1 != null) {
				try {
					ps1.close();
				} catch (SQLException e) {
					// log
				}
			}

			if (ps2 != null) {
				try {
					ps2.close();
				} catch (SQLException e) {
					// log
				}
			}

			cp.closeConnection(con, st);
		}

		return true;
	}

	@Override
	public boolean appointmentTest(String title, String login) throws DAOException {

		String sql11 = "SELECT user.id, tests.id FROM user, tests \r\n" + "WHERE user.login = ? AND tests.title = ?;";
		String sql22 = "INSERT INTO user_has_tests (user_id, tests_id, date_appointment) VALUES (?,?,?);";

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection con = cp.takeConnection();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		int userId;
		int testId;

		try {
			ps = con.prepareStatement(sql11);
			ps.setString(1, login);
			ps.setString(2, title);
			rs = ps.executeQuery();
			if (rs.next()) {
				userId = rs.getInt(1);
				testId = rs.getInt(2);
			} else {
				return false;
			}

			ps2 = con.prepareStatement(sql22);
			ps2.setInt(1, userId);
			ps2.setInt(2, testId);
			LocalDate d = LocalDate.now();
			java.sql.Date date = java.sql.Date.valueOf(d);
			ps2.setDate(3, date);
			ps2.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			cp.closeConnection(con, ps, ps2, rs);
		}

		return true;
	}

	@Override
	public List<TestInfo> findNoCompletedTests(int userId) throws DAOException {

		String sql3 = "SELECT user_has_tests.id, user_has_tests.date_appointment, tests.id, tests.title, tests.time "
				+ " FROM user_has_tests \r\n"
				+ "INNER JOIN tests ON user_has_tests.tests_id = tests.id AND user_id = ?\r\n"
				+ "WHERE tests.id = ANY (SELECT  tests_id  FROM user_has_tests\r\n"
				+ "WHERE user_id = ? AND status = 'no completed');";

		List<TestInfo> tests = new ArrayList<TestInfo>();
		ConnectionPool cp = ConnectionPool.getInstance();
		Connection con = cp.takeConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(sql3);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				TestInfo t = new TestInfo();
				t.setPassId(rs.getInt(1));
				t.setId(rs.getInt(3));
				t.setTitle(rs.getString(4));
				LocalDate loc = rs.getDate(2).toLocalDate();
				t.setDateAppointment(loc);
				LocalTime time = rs.getTime(5).toLocalTime();
				t.setTimePass(time);
				tests.add(t);

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			cp.closeConnection(con, ps, rs);
		}

		return tests;
	}

	@Override
	public Test takeTest(int testId) throws DAOException {

		Test test = new Test();
		ConnectionPool cp = ConnectionPool.getInstance();
		Connection con = cp.takeConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		

		String sql1 = "SELECT * FROM tests WHERE tests.id = ?;";
		String sql2 = "SELECT id, text FROM test_questions WHERE tests_id = ?;";

		try {
			ps = con.prepareStatement(sql1);
			ps.setInt(1, testId);
			rs = ps.executeQuery();
			if (rs.next()) {
				test.setId(rs.getInt(1));
				test.setTitle(rs.getString(2));
				test.setTimePass(rs.getTime(3).toLocalTime());
			}

			ps2 = con.prepareStatement(sql2);
			ps2.setInt(1, testId);
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				Question question = new Question();
				question.setId(rs2.getInt(1));
				question.setContent(rs2.getString(2));
				test.setQuestion(question);
				addAnswerToQuestion(con,  question);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			cp.closeConnection(ps2, rs2);
			cp.closeConnection(con, ps, rs);
		}

		return test;
	}

	private void addAnswerToQuestion(Connection con,  Question question) throws SQLException {

		String sql3 = "SELECT id, test_answer_variance, isRight FROM test_answer WHERE test_questions_id = ?;";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql3);
			ps.setInt(1, question.getId());
			rs = ps.executeQuery();

			while (rs.next()) {
				Answer answer = new Answer();
				answer.setId(rs.getInt(1));
				answer.setContent(rs.getString(2));
				int isRight = rs.getInt(3);
				if (isRight == 0) {
					answer.setRightness(false);
				} else {
					answer.setRightness(true);
				}
				question.setAnswer(answer);
			}
			
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				// log
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				// log
			}
		}

	}

}
