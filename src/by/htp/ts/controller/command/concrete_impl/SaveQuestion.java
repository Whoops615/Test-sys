package by.htp.ts.controller.command.concrete_impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.command.constant.OtherConstant;
import by.htp.ts.controller.command.constant.RedirectConstant;
import by.htp.ts.controller.command.constant.RequestParameter;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.TestService;
import by.htp.ts.service.validator.ValidatorException;

public class SaveQuestion implements Command {

	private String QUESTION_SAVE_TRUE = "Question saved successfully";
	private String QUESTION_SAVE_FALSE = "Test no find! The question is not saved, try again";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Map<String, String> question = mapParameterQuestion(request);
		ServiceProvider provider = ServiceProvider.getInstance();
		TestService ts = provider.getTestService();

		try {
			
			if (!ts.saveQuestion(question)) {
				response.sendRedirect(RedirectConstant.REDIRECT_TEACHER_MESSAGE + QUESTION_SAVE_FALSE);
			}
			
				response.sendRedirect(RedirectConstant.REDIRECT_TEACHER_MESSAGE + QUESTION_SAVE_TRUE);
			
		} catch (ValidatorException message) {
			response.sendRedirect(RedirectConstant.REDIRECT_TEACHER_MESSAGE + message.getMessage());
		} catch (ServiceException e) {
			// log
			response.sendRedirect(RedirectConstant.REDIRECT_ERROR + OtherConstant.TECHNICAL_PROBLEMS);
		}

	}

	private Map<String, String> mapParameterQuestion(HttpServletRequest request) {

		Map<String, String> question = new HashMap<String, String>();

		question.put(RequestParameter.TITLE, request.getParameter("title"));
		question.put(RequestParameter.TEXT, request.getParameter("text"));

		question.put(RequestParameter.ANSWER_1, request.getParameter("answer1"));
		question.put(RequestParameter.ANSWER_2, request.getParameter("answer2"));
		question.put(RequestParameter.ANSWER_3, request.getParameter("answer3"));
		question.put(RequestParameter.ANSWER_4, request.getParameter("answer4"));

		question.put(RequestParameter.RESULT_1, request.getParameter("result1"));
		question.put(RequestParameter.RESULT_2, request.getParameter("result2"));
		question.put(RequestParameter.RESULT_3, request.getParameter("result3"));
		question.put(RequestParameter.RESULT_4, request.getParameter("result4"));

		question.put(RequestParameter.INCLUDE1, request.getParameter("include1"));
		question.put(RequestParameter.INCLUDE2, request.getParameter("include2"));
		question.put(RequestParameter.INCLUDE3, request.getParameter("include3"));
		question.put(RequestParameter.INCLUDE4, request.getParameter("include4"));
		return question;
	}

}
