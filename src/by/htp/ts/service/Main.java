package by.htp.ts.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.htp.ts.bean.Answer;
import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.TestInfo;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.impl.SQLTestDAO;




public class Main {

	public static void main(String[] args) throws DAOException, ServiceException, SQLException {
		
	/*	
		int testId = 12;
		SQLTestDAO dao = new SQLTestDAO();
		
		
		Test test = dao.takeTest(testId);
		System.out.println(test.getId());
		System.out.println(test.getTitle());
		System.out.println(test.getTimePass());
		System.out.println("---------");
		for (Question q : test.getQuestions()) {
			System.out.println("Question : "+q.getId()  + " "+q.getContent());
			for(Answer a : q.getAnswers()) {
				System.out.println(a);
			}
			System.out.println("-----------------------");
		}

		int userId = 4;
		
		
		List<TestInfo> list= dao.findNoCompletedTests(userId);
		System.out.println(list);
		*/
		
		List<Boolean> list = new ArrayList<Boolean>();
		list.add(true);
		list.add(true);
		list.add(2, false);
		System.out.println(list);

	}

}
