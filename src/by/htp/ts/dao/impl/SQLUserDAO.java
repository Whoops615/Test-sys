package by.htp.ts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.htp.ts.bean.User;
import by.htp.ts.bean.UserInfo;
import by.htp.ts.bean.UserParameter;
import by.htp.ts.bean.creator.Creator;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.UserDAO;
import by.htp.ts.dao.connect_pool.ConnectionPool;

public class SQLUserDAO implements UserDAO {

	private static Lock lock = new ReentrantLock();

	private final String SQL_SELECT_LOGIN = "SELECT login FROM user WHERE login = ?;";
	private final String SQL_INSERT_USER_DETAIL = "INSERT INTO user_detail (name, surname) Values(?,?);";
	private final String SQL_SET_ID = "SET @id = LAST_INSERT_ID();";
	private final String SQL_INSERT_USER = "INSERT INTO user (login, password, email, user_detail_id, user_role_id)\r\n"
			+ "VALUES (?,?,?,@id, (SELECT id FROM user_role WHERE role = ?));";
	private final String SQL_SELECT_USER = "SELECT user.id , user.email , "
			+ "user_role.role , user_detail.name , user_detail.surname\r\n" + "FROM user\r\n"
			+ "INNER JOIN user_detail ON user.user_detail_id = user_detail.id\r\n"
			+ "INNER JOIN user_role ON user.user_role_id = user_role.id " + "WHERE user.login = ? "
			+ "AND user.password = ?;";
	
	@Override
	public User authorization(String login, String password) throws DAOException {

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection con = cp.takeConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;

		Map<String, Object> userParameter;
		Creator creator = Creator.getInstance();

		try {
			ps = con.prepareStatement(SQL_SELECT_USER);
			ps.setString(1, login);
			ps.setString(2, password);

			rs = ps.executeQuery();

			if (rs.next()) {
				userParameter = new HashMap<String, Object>();
				
				userParameter.put(UserParameter.ID.name(), rs.getInt(1));
				userParameter.put(UserParameter.EMAIL.name(), rs.getString(2));
				userParameter.put(UserParameter.ROLE.name(), rs.getString(3));
				userParameter.put(UserParameter.NAME.name(), rs.getString(4));
				userParameter.put(UserParameter.SURNAME.name(), rs.getString(5));

				user = creator.createUser(userParameter);

			} else {
				user = null;
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {

			cp.closeConnection(con, ps, rs);
		}

		return user;
	}

	@Override
	public boolean registration(UserInfo user) throws DAOException {

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection con = cp.takeConnection();
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps2 = null;

		try {

			lock.lock();
			ps2 = con.prepareStatement(SQL_SELECT_LOGIN);
			ps2.setString(1, user.getLogin());
			rs = ps2.executeQuery();
			if (rs.next()) {
				return false;
			}

			con.setAutoCommit(false);

			ps1 = con.prepareStatement(SQL_INSERT_USER_DETAIL);
			ps1.setString(1, user.getName());
			ps1.setString(2, user.getSurname());
			ps1.executeUpdate();

			st = con.createStatement();
			st.executeUpdate(SQL_SET_ID);

			ps3 = con.prepareStatement(SQL_INSERT_USER);
			ps3.setString(1, user.getLogin());
			ps3.setString(2, user.getPassword());
			ps3.setString(3, user.getEmail());
			ps3.setString(4, user.getRole());
			ps3.executeUpdate();

			con.commit();

		}

		catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// log
			}
			throw new DAOException(e);

		} finally {
			lock.unlock();
			closeResourceAuthorization(cp, con, st, rs, ps1, ps2, ps3);
		}

		return true;
	}

	private void closeResourceAuthorization(ConnectionPool cp, Connection con, Statement st, ResultSet rs,
			PreparedStatement ps1, PreparedStatement ps2, PreparedStatement ps3) {
		try {
			if (ps1 != null) {
				ps1.close();
			}
		} catch (SQLException e) {
			// log
		}
		try {
			if (ps3 != null) {
				ps3.close();
			}
		} catch (SQLException e) {
			// log
		}
		try {

			if (ps2 != null) {
				ps2.close();
			}
		} catch (SQLException e) {
			// log
		}

		cp.closeConnection(con, st, rs);

	}

}
