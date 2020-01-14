package by.htp.ts.controller.command.test_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.RequestInfo;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.constant.ResponseMessage;
import by.htp.ts.controller.constant.Transition;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.TestService;
import by.htp.ts.service.validator.ValidatorException;

public class AppointmentTest implements Command {

	private final String APPOINTMENT_SUCCESSFUL = "Test successfully assigned to user";
	private final String APPOINTMENT_NOT_SUCCESSFUL = "Test not assigned, no such test or user";
	private RequestInfo requestInfo = new RequestInfo();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter("title");
		String login = request.getParameter("login");

		ServiceProvider provider = ServiceProvider.getInstance();
		TestService testService = provider.getTestService();

		HttpSession session = request.getSession(true);

		String url = null;
		String mes = null;

		try {

			if (testService.appointmentTest(title, login)) {
				url = Transition.PAGE_TEACHER;
				mes = APPOINTMENT_SUCCESSFUL;
				request.setAttribute("message", mes);
				RequestDispatcher disp = request.getRequestDispatcher(url);
				disp.forward(request, response);
			} else {
				url = Transition.REDIRECT_APPOINTMENT_TEST + Transition.REDIRECT_MESSAGE + APPOINTMENT_NOT_SUCCESSFUL;
				response.sendRedirect(url);
			}

		} catch (ValidatorException message) {
			url = Transition.PAGE_APPOINTMENT_TEST;
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
