package by.htp.ts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import java.time.LocalTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.htp.ts.bean.Answer;
import by.htp.ts.bean.Question;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.TestDAO;
import by.htp.ts.dao.connect_pool.ConnectionPool;

public class SQLTestDAO implements TestDAO {

	private static Lock lock = new ReentrantLock();

	private String SQL_INSERT_TEST = "INSERT INTO tests (title, time) VALUES (?,?)";
	private String SQL_SELECT_TEST = "SELECT id, title FROM tests WHERE title = ?;";

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

		// не ругаться, все находится в режиме доработки! эти строки будут вынесены отсюда куда надо позже!
		
		String SQL_ID = "SET @id = LAST_INSERT_ID();";
		String INSERT_QUESTION_TEXT = "INSERT INTO test_questions (text, tests_id) VALUE (?,?)";
		String qiqiqi = "INSERT INTO test_answer (test_answer_variance, isRight, test_questions_id)\r\n"
				+ "VALUES (?, ?, @id);";

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
			ps1.setString(1, question.getText());
			ps1.setInt(2, testId);
			ps1.executeUpdate();

			st = con.createStatement();
			st.executeUpdate(SQL_ID);

			ps2 = con.prepareStatement(qiqiqi);

			for (Answer a : question.getAnswers()) {

				ps2.setString(1, a.getText());

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

}
