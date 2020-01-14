package by.htp.ts.service;

import java.util.List;

import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.TestInfo;
import by.htp.ts.service.validator.ValidatorException;

public interface TestService {

	boolean createTest(String title, String time) throws ValidatorException, ServiceException;
	boolean saveQuestion(String title, Question question) throws ValidatorException, ServiceException;
	boolean appointmentTest(String title, String login) throws ValidatorException, ServiceException;
	List<TestInfo> findNoCompletedTests(int userId) throws ValidatorException, ServiceException;
	Test takeTest(int testId) throws ServiceException;
}
