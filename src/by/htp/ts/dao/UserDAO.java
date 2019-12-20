package by.htp.ts.dao;

import by.htp.ts.bean.User;
import by.htp.ts.bean.UserInfo;

public interface UserDAO {
	
	User authorization(String login, String password) throws DAOException;
	boolean registration(UserInfo userInfo) throws DAOException;

}
