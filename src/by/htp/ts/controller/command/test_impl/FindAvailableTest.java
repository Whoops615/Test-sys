package by.htp.ts.controller.command.test_impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.RequestInfo;
import by.htp.ts.bean.TestInfo;
import by.htp.ts.bean.User;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.constant.ResponseMessage;
import by.htp.ts.controller.constant.Transition;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.TestService;
import by.htp.ts.service.validator.ValidatorException;

public class FindAvailableTest implements Command {

	private String NOT_ASSIGNED_TEST = "you are not assigned tests";
	private String ASSIGNED_TEST = "List of tests assigned to you";
	private RequestInfo requestInfo = new RequestInfo();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceProvider provider = ServiceProvider.getInstance();
		TestService testService = provider.getTestService();
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		List<TestInfo> tests;

		String url = null;
		String mes = null;

		if (user == null) {
			url = Transition.PAGE_AUTHORIZATION;
			mes = ResponseMessage.SESSION_EXPIRED;

			requestInfo.setUrl(url);
			requestInfo.setMessage(mes);
			session.setAttribute("requestInfo", requestInfo);

			request.setAttribute("message", mes);
			RequestDispatcher disp = request.getRequestDispatcher(url);
			disp.forward(request, response);
			return;
		}

		int userId = user.getId();

		try {
			tests = testService.findNoCompletedTests(userId);

			if (tests.isEmpty()) {
				url = Transition.PAGE_STUDENT;
				mes = NOT_ASSIGNED_TEST;
				request.setAttribute("message", mes);
			} else {
				url = Transition.PAGE_VIEW_APPOINTMENT_TEST;
				mes = ASSIGNED_TEST;
				request.setAttribute("message", mes);
				request.setAttribute("tests", tests);
			}
			RequestDispatcher disp = request.getRequestDispatcher(url);
			disp.forward(request, response);

		} catch (ValidatorException message) {
			url = Transition.PAGE_AUTHORIZATION;
			mes = message.getMessage();
			request.setAttribute("message", mes);
			session.removeAttribute("user");
			RequestDispatcher disp = request.getRequestDispatcher(url);
			disp.forward(request, response);
		} catch (ServiceException e) {
			// log
			url = Transition.REDIRECT_ERROR + Transition.REDIRECT_MESSAGE + ResponseMessage.TECHNICAL_PROBLEMS;
			response.sendRedirect(url);
		}

		if (mes != null) {
			requestInfo.setMessage(mes);
		}
		requestInfo.setUrl(url);
		session.setAttribute("requestInfo", requestInfo);

	}

}
