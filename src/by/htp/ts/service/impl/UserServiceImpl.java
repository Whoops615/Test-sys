package by.htp.ts.service.impl;

import by.htp.ts.bean.User;
import by.htp.ts.bean.UserInfo;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.DAOProvider;
import by.htp.ts.dao.UserDAO;

import by.htp.ts.service.ServiceException;
import by.htp.ts.service.UserService;
import by.htp.ts.service.validator.Validator;
import by.htp.ts.service.validator.ValidatorException;

public class UserServiceImpl implements UserService {

	@Override
	public User authorization(String login, String password) throws ServiceException, ValidatorException {

		if (!Validator.validLogin(login)) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_LOGIN);
		}
		if (!Validator.validPassword(password)) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_PASSWORD);
		}

		DAOProvider provider = DAOProvider.getInstance();
		UserDAO userDao = provider.getUserDAO();
		User user;

		try {
			user = userDao.authorization(login, password);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return user;
	}

	@Override
	public boolean registration(UserInfo userInfo) throws ServiceException, ValidatorException {

		if (!Validator.validLogin(userInfo.getLogin())) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_LOGIN);
		}
		if (!Validator.validPassword(userInfo.getPassword())) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_PASSWORD);
		}
		if (!Validator.validRole(userInfo.getRole())) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_ROLE);
		}
		if (!Validator.validEmail(userInfo.getEmail())) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_EMAIL);
		}
		if (!Validator.validNameOrSurname(userInfo.getName())) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_NAME);
		}
		if (!Validator.validNameOrSurname(userInfo.getSurname())) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_SURNAME);
		}

		DAOProvider provider = DAOProvider.getInstance();
		UserDAO userDao = provider.getUserDAO();

		try {

			return userDao.registration(userInfo);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

}
