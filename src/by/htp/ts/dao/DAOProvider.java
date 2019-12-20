package by.htp.ts.dao;

import by.htp.ts.dao.impl.SQLTestDAO;
import by.htp.ts.dao.impl.SQLUserDAO;

public final class DAOProvider {

	private static final DAOProvider INSTANCE = new DAOProvider();

	private final UserDAO userDAO = new SQLUserDAO();
	private final TestDAO testDAO = new SQLTestDAO();

	private DAOProvider() {

	}

	public static DAOProvider getInstance() {
		return INSTANCE;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public TestDAO getTestDAO() {
		return testDAO;
	}

}
