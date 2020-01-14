package by.htp.ts.service.impl;

import java.time.LocalTime;
import java.util.List;

import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.TestInfo;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.DAOProvider;
import by.htp.ts.dao.TestDAO;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.TestService;
import by.htp.ts.service.validator.Validator;
import by.htp.ts.service.validator.ValidatorException;

public class TestServiceImpl implements TestService {

	@Override
	public boolean createTest(String title, String time) throws ValidatorException, ServiceException {

		if (!Validator.validTestTitle(title)) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_TEST_TITLE);
		}

		LocalTime locTime = parsingTime(time);

		DAOProvider provider = DAOProvider.getInstance();
		TestDAO testDao = provider.getTestDAO();

		try {

			return testDao.saveTest(title, locTime);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	private LocalTime parsingTime(String time) throws ValidatorException {

		String[] str = time.split(":");

		int hour = Integer.parseInt(str[0]);
		int minute = Integer.parseInt(str[1]);

		if (!Validator.validTestTime(hour, minute)) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_TEST_TIME);
		}

		LocalTime locTime = LocalTime.of(hour, minute);

		return locTime;
	}

	@Override
	public boolean saveQuestion(String title, Question question) throws ValidatorException, ServiceException {

		if (!Validator.validTestTitle(title)) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_TEST_TITLE);
		}

		if (!Validator.validQuestion(question)) {
			throw new ValidatorException();
		}

		DAOProvider provider = DAOProvider.getInstance();
		TestDAO testDao = provider.getTestDAO();

		try {

			return testDao.saveQuestion(title, question);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public boolean appointmentTest(String title, String login) throws ValidatorException, ServiceException {

		if (!Validator.validTestTitle(title)) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_TEST_TITLE);
		}

		if (!Validator.validLogin(login)) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_LOGIN);
		}

		DAOProvider provider = DAOProvider.getInstance();
		TestDAO testDao = provider.getTestDAO();

		try {
			return testDao.appointmentTest(title, login);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<TestInfo> findNoCompletedTests(int userId) throws ValidatorException, ServiceException {

		if (!Validator.validUserId(userId)) {
			throw new ValidatorException("Login problems, try logging in again");
		}

		DAOProvider provider = DAOProvider.getInstance();
		TestDAO testDao = provider.getTestDAO();
		List<TestInfo> tests;
		try {
			tests = testDao.findNoCompletedTests(userId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return tests;
	}

	@Override
	public Test takeTest(int testId) throws ServiceException {

		DAOProvider provider = DAOProvider.getInstance();
		TestDAO testDao = provider.getTestDAO();
		Test test;
		try {
			test = testDao.takeTest(testId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return test;
	}

}
