package by.htp.ts.service;

import java.util.Map;

import by.htp.ts.service.validator.ValidatorException;

public interface TestService {
	
	boolean createTest(String title, String time) throws ValidatorException, ServiceException;
	boolean saveQuestion(Map<String, String> question) throws ValidatorException, ServiceException;

}
