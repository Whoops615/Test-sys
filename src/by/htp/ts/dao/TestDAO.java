package by.htp.ts.dao;

import java.time.LocalTime;
import java.util.List;

import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.TestInfo;

public interface TestDAO {
	
	boolean saveTest(String title, LocalTime loc) throws DAOException;
	boolean saveQuestion(String title, Question question) throws DAOException;
	boolean appointmentTest(String title, String login) throws DAOException;
	List<TestInfo> findNoCompletedTests(int userId) throws DAOException;
	Test takeTest(int testId) throws DAOException;
}
