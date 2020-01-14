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

public class StartTest implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//создаем все нужные обьекты и редирект на страницу некст тест
		// записываем время старта и редирекс на некст вопрос
		HttpSession session = request.getSession(false);
		Test test = (Test) session.getAttribute("test");
		TestPassInfo testPassInfo = (TestPassInfo) session.getAttribute("testPassInfo");
		Question question = test.getQuestion(testPassInfo.getCountQuestion());
		//System.out.println(question);
		int count = 0;
		for (Answer a : question.getAnswers()) {
			if(a.isRightness()) {
				count++;
			}
		}
		//System.out.println(count);
		String url = null;
		if(count == 1) {
			//System.out.println("1 true");
			url = Transition.PAGE_QUESTION_RADIO;
		} else {
			//System.out.println("more true");
			url = Transition.PAGE_QUESTION_BOX;
		}
		
		request.setAttribute("question", question);
		RequestDispatcher disp = request.getRequestDispatcher(url);
		disp.forward(request, response);
		

	}

}
