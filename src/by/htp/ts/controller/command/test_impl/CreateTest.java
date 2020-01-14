package by.htp.ts.controller.command.test_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.RequestInfo;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.constant.RequestParameter;
import by.htp.ts.controller.constant.ResponseMessage;
import by.htp.ts.controller.constant.Transition;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.TestService;
import by.htp.ts.service.validator.ValidatorException;

public class CreateTest implements Command {

	private final String TEST_SAVE_TRUE = "Test created successfully";
	private final String TEST_SAVE_FALSE = "A test with the same name already exists, try again";
	private RequestInfo requestInfo = new RequestInfo();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter(RequestParameter.TITLE);
		String time = request.getParameter(RequestParameter.TIME);

		ServiceProvider provider = ServiceProvider.getInstance();
		TestService testService = provider.getTestService();

		HttpSession session = request.getSession(true);

		String url = null;
		String mes = null;

		try {

			if (testService.createTest(title, time)) {
				url = Transition.PAGE_TEACHER;
				mes = TEST_SAVE_TRUE;
				request.setAttribute("message", mes);
				RequestDispatcher disp = request.getRequestDispatcher(url);
				disp.forward(request, response);

			} else {
				url = Transition.REDIRECT_TEACHER + Transition.REDIRECT_MESSAGE + TEST_SAVE_FALSE;
				response.sendRedirect(url);
			}

		} catch (ValidatorException message) {
			url = Transition.PAGE_CREATE_TEST;
			mes = message.getMessage();
			request.setAttribute("message", mes);
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
