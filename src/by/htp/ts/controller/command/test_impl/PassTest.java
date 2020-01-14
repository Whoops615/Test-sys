package by.htp.ts.controller.command.test_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.RequestInfo;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.TestPassInfo;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.constant.ResponseMessage;
import by.htp.ts.controller.constant.Transition;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.TestService;

public class PassTest implements Command {

	private RequestInfo requestInfo = new RequestInfo();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int testId = Integer.parseInt(request.getParameter("testId"));
		int passId = Integer.parseInt(request.getParameter("passId"));

		ServiceProvider provider = ServiceProvider.getInstance();
		TestService testService = provider.getTestService();

		HttpSession session = request.getSession(false);
		Test test;
		TestPassInfo testPassInfo = new TestPassInfo();

		String url = null;

		try {

			test = testService.takeTest(testId);
			session.setAttribute("test", test);
			
			testPassInfo.setPassId(passId);
			session.setAttribute("testPassInfo", testPassInfo);
			

			url = Transition.PAGE_START_TEST;
			RequestDispatcher disp = request.getRequestDispatcher(url);
			disp.forward(request, response);

		} catch (ServiceException e) {
			// log
			url = Transition.REDIRECT_ERROR + Transition.REDIRECT_MESSAGE + ResponseMessage.TECHNICAL_PROBLEMS;
			response.sendRedirect(url);
		}

		requestInfo.setUrl(url);
		session.setAttribute("requestInfo", requestInfo);

	}

}
