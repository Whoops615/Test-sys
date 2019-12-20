package by.htp.ts.service.impl;

import java.time.LocalTime;
import java.util.Map;

import by.htp.ts.bean.Answer;
import by.htp.ts.bean.Question;
import by.htp.ts.controller.command.constant.RequestParameter;
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
	public boolean saveQuestion(Map<String, String> question) throws ValidatorException, ServiceException {

		String title = question.get(RequestParameter.TITLE);

		if (!Validator.validTestTitle(title)) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_TEST_TITLE);
		}

		String text = question.get(RequestParameter.TEXT);

		if (!Validator.validTextQuestion(text)) {
			throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_TEXT_QUESTION);

		}

		String answer1 = question.get(RequestParameter.ANSWER_1).trim();
		String result1 = question.get(RequestParameter.RESULT_1);
		String include1 = question.get(RequestParameter.INCLUDE1);

		Answer a1 = createAnswer(answer1, result1, include1);

		String answer2 = question.get(RequestParameter.ANSWER_2).trim();
		String result2 = question.get(RequestParameter.RESULT_2);
		String include2 = question.get(RequestParameter.INCLUDE2);

		Answer a2 = createAnswer(answer2, result2, include2);

		String answer3 = question.get(RequestParameter.ANSWER_3).trim();
		String result3 = question.get(RequestParameter.RESULT_3);
		String include3 = question.get(RequestParameter.INCLUDE3);

		Answer a3 = createAnswer(answer3, result3, include3);

		String answer4 = question.get(RequestParameter.ANSWER_4).trim();
		String result4 = question.get(RequestParameter.RESULT_4);
		String include4 = question.get(RequestParameter.INCLUDE4);

		Answer a4 = createAnswer(answer4, result4, include4);

		Question q = new Question();

		q.setText(text);

		if (a1 != null) {
			q.setAnswer(a1);
		}
		if (a2 != null) {
			q.setAnswer(a2);
		}
		if (a3 != null) {
			q.setAnswer(a3);
		}
		if (a4 != null) {
			q.setAnswer(a4);
		}

		DAOProvider provider = DAOProvider.getInstance();
		TestDAO testDao = provider.getTestDAO();

		try {

			return testDao.saveQuestion(title, q);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	private Answer createAnswer(String answer, String result, String include) throws ValidatorException {

		if (include != null) {

			if (!Validator.validAnswer(answer, result)) {
				throw new ValidatorException(ErrorMessage.ERROR_MESSAGE_ANSWER);
			}

			Answer a = new Answer();
			a.setText(answer);

			if (result.equals("true")) {
				a.setRightness(true);
			} else {
				a.setRightness(false);
			}

			return a;

		}

		return null;
	}

}
