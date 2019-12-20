package by.htp.ts.dao;

import java.time.LocalTime;

import by.htp.ts.bean.Question;

public interface TestDAO {
	
	boolean saveTest(String title, LocalTime loc) throws DAOException;
	boolean saveQuestion(String title, Question question) throws DAOException;

}
