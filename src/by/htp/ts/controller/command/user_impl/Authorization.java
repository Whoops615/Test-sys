package by.htp.ts.controller.command.user_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.RequestInfo;
import by.htp.ts.bean.User;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.constant.Transition;
import by.htp.ts.controller.constant.RequestParameter;
import by.htp.ts.controller.constant.ResponseMessage;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.UserService;
import by.htp.ts.service.validator.ValidatorException;

public class Authorization implements Command {

	private final String NO_SUCH_USER = "No such user, try again or register";
	private final String AUTHORIZATION_WELCOM = "Hello, you are successfully logged in.";
	private RequestInfo requestInfo = new RequestInfo();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter(RequestParameter.LOGIN);
		String password = request.getParameter(RequestParameter.PASSWORD);

		ServiceProvider provider = ServiceProvider.getInstance();
		UserService userService = provider.getUserService();

		HttpSession session = request.getSession(true);

		User user;

		String url = null;
		String mes = null;

		try {
			user = userService.authorization(login, password);

			if (user == null) {
				url = Transition.REDIRECT_AUTHORIZATION + Transition.REDIRECT_MESSAGE + NO_SUCH_USER;

				requestInfo.setUrl(url);

				session.setAttribute("requestInfo", requestInfo);
				response.sendRedirect(url);
				return;
			}

			url = checkRole(user) + Transition.REDIRECT_MESSAGE + AUTHORIZATION_WELCOM;
			session.setAttribute("user", user);
			response.sendRedirect(url);

		} catch (ServiceException e) {
			// log
			url = Transition.REDIRECT_ERROR + Transition.REDIRECT_MESSAGE + ResponseMessage.TECHNICAL_PROBLEMS;
			response.sendRedirect(url);
		} catch (ValidatorException message) {
			mes = message.getMessage();
			url = Transition.PAGE_AUTHORIZATION;
			request.setAttribute("message", mes);
			RequestDispatcher disp = request.getRequestDispatcher(url);
			disp.forward(request, response);
		}

		if (mes != null) {
			requestInfo.setMessage(mes);
		}
		requestInfo.setUrl(url);
		session.setAttribute("requestInfo", requestInfo);

	}

	private String checkRole(User user) {

		String role = user.getRole();

		switch (role) {
		case "teacher":
			return Transition.REDIRECT_TEACHER;
		case "student":
			return Transition.REDIRECT_STUDENT;
		}

		return null;
	}

}
