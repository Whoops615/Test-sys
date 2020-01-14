package by.htp.ts.controller.command.test_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.Answer;
import by.htp.ts.bean.Question;
import by.htp.ts.bean.RequestInfo;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.command.util.InitialCheck;
import by.htp.ts.controller.constant.ResponseMessage;
import by.htp.ts.controller.constant.Transition;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.TestService;
import by.htp.ts.service.validator.ValidatorException;

public class SaveQuestion implements Command {

	private String QUESTION_SAVE_TRUE = "Question saved successfully";
	private String QUESTION_SAVE_FALSE = "Test no find! The question is not saved, try again";
	private String INVALID_QUESTION = "The question is not correctly compiled";
	private RequestInfo requestInfo = new RequestInfo();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Question question = createQuestion(request);
		String title = request.getParameter("title");

		ServiceProvider provider = ServiceProvider.getInstance();
		TestService testService = provider.getTestService();

		HttpSession session = request.getSession(true);

		String url = null;
		String mes = null;

		if (question == null) {
			url = Transition.PAGE_CREATE_QUESTION;
			mes = INVALID_QUESTION;
			request.setAttribute("message", mes);		
			requestInfo.setUrl(url);
			requestInfo.setMessage(mes);
			session.setAttribute("requestInfo", requestInfo);
			RequestDispatcher disp = request.getRequestDispatcher(url);
			disp.forward(request, response);
			return;
		}

		try {

			if (!testService.saveQuestion(title, question)) {
				url = Transition.REDIRECT_CREATE_QUESTION + Transition.REDIRECT_MESSAGE + QUESTION_SAVE_FALSE;
				response.sendRedirect(url);
			} else {
				url = Transition.REDIRECT_TEACHER + Transition.REDIRECT_MESSAGE + QUESTION_SAVE_TRUE;
				response.sendRedirect(url);
			}

		} catch (ValidatorException e) {
			url = Transition.PAGE_CREATE_QUESTION;
			mes = INVALID_QUESTION;
			request.setAttribute("message", mes);
			RequestDispatcher disp = request.getRequestDispatcher(url);
			disp.forward(request, response);
		} catch (ServiceException e) {
			url = Transition.REDIRECT_ERROR + Transition.REDIRECT_MESSAGE + ResponseMessage.TECHNICAL_PROBLEMS;
			response.sendRedirect(url);
		}

		if (mes != null) {
			requestInfo.setMessage(mes);
		}
		requestInfo.setUrl(url);
		session.setAttribute("requestInfo", requestInfo);

	}

	private Question createQuestion(HttpServletRequest request) {

		Question question = new Question();

		String content = request.getParameter("text");
		if (!InitialCheck.checkString(content)) {
			return null;
		}
		question.setContent(content);

		if (request.getParameter("include1") != null) {
			String answer1 = request.getParameter("answer1");
			String result1 = request.getParameter("result1");
			addAnswer(question, answer1, result1);
		}

		if (request.getParameter("include2") != null) {
			String answer2 = request.getParameter("answer2");
			String result2 = request.getParameter("result2");
			addAnswer(question, answer2, result2);
		}

		if (request.getParameter("include3") != null) {
			String answer3 = request.getParameter("answer3");
			String result3 = request.getParameter("result3");
			addAnswer(question, answer3, result3);
		}

		if (request.getParameter("include4") != null) {
			String answer4 = request.getParameter("answer4");
			String result4 = request.getParameter("result4");
			addAnswer(question, answer4, result4);
		}

		return question;
	}

	private void addAnswer(Question question, String answer, String result) {

		if (InitialCheck.checkAnswer(answer, result)) {
			Answer a1 = new Answer();
			a1.setContent(answer);

			if (result.equals("true")) {
				a1.setRightness(true);
			} else {
				a1.setRightness(false);
			}
			question.setAnswer(a1);
		}

	}

}
