package by.htp.ts.controller.command.test_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.Answer;
import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.TestPassInfo;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.constant.Transition;

public class NextQuestion implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		TestPassInfo testPassInfo = (TestPassInfo) session.getAttribute("testPassInfo");
		String type = request.getParameter("type");

		int countTrueAnswer = testPassInfo.getTrueAnswer();
		boolean result;

		if (type.equals("radio")) {
			result = Boolean.parseBoolean(request.getParameter("result"));
			if (result) {
				countTrueAnswer++;
			}
		} else {
			String[] mas = request.getParameterValues("checkbox[]");
			result = checkAnswer(mas);
			if (result) {
				countTrueAnswer++;			
			}

		}	
		testPassInfo.setTrueAnswer(countTrueAnswer);

		int countQuestion = testPassInfo.getCountQuestion();

		Test test = (Test) session.getAttribute("test");
		int size = test.getQuestions().size();
		
		String url = null;
		if (countQuestion < size - 1) {
			countQuestion++;
			testPassInfo.setCountQuestion(countQuestion);
			Question question = test.getQuestion(countQuestion);
			url = checkCountTrueAnswer(question);
			request.setAttribute("question", question);
			RequestDispatcher disp = request.getRequestDispatcher(url);
			disp.forward(request, response);
		} else {

			url = Transition.PAGE_STUDENT;
			String mes = "Result pass test: " + testPassInfo.getTrueAnswer() + "/ " + size;
			request.setAttribute("message", mes);
			RequestDispatcher disp = request.getRequestDispatcher(url);
			disp.forward(request, response);
		}

	}

	private boolean checkAnswer(String[] mas) {

		for (String str : mas) {
			if (str.equals("false")) {
				return false;
			}
		}

		return true;
	}

	private String checkCountTrueAnswer(Question q) {
		String url = null;
		int count = 0;
		for (Answer a : q.getAnswers()) {
			if (a.isRightness()) {
				count++;
			}
		}
		if (count == 1) {
			url = Transition.PAGE_QUESTION_RADIO;
		} else {
			url = Transition.PAGE_QUESTION_BOX;
		}
		return url;
	}

}
