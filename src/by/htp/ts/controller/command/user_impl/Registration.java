package by.htp.ts.controller.command.user_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.RequestInfo;
import by.htp.ts.bean.UserInfo;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.command.util.InitialCheck;

import by.htp.ts.controller.constant.Transition;
import by.htp.ts.controller.constant.RequestParameter;
import by.htp.ts.controller.constant.ResponseMessage;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.UserService;
import by.htp.ts.service.validator.ValidatorException;

public class Registration implements Command {

	private final String REGISTRATION_FAIL = "A user with that username already exists!";
	private final String REGISTRATION_WELCOM = "Congratulations. You have successfully registered, please log in";
	private RequestInfo requestInfo = new RequestInfo();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceProvider provider = ServiceProvider.getInstance();
		UserService service = provider.getUserService();
		UserInfo userInfo;
		userInfo = createUserInfo(request);
		HttpSession session = request.getSession(true);

		String mes = null;
		String url = null;

		if (userInfo == null) {
			url = Transition.PAGE_REGISTRATION;
			mes = ResponseMessage.INCORRECT_DATA;
			request.setAttribute("message", mes);
			RequestDispatcher disp = request.getRequestDispatcher(url);
			disp.forward(request, response);
			
			requestInfo.setMessage(mes);
			requestInfo.setUrl(url);
			session.setAttribute("requestInfo", requestInfo);
				
			return;
		}

		try {

			if (service.registration(userInfo)) {
				url = Transition.REDIRECT_AUTHORIZATION + Transition.REDIRECT_MESSAGE + REGISTRATION_WELCOM;
				response.sendRedirect(url);

			} else {
				url = Transition.PAGE_REGISTRATION;
				mes = REGISTRATION_FAIL;
				request.setAttribute("message", mes);
				RequestDispatcher disp = request.getRequestDispatcher(url);
				disp.forward(request, response);

			}

		} catch (ServiceException e) {
			// log
			url = Transition.REDIRECT_ERROR + Transition.REDIRECT_MESSAGE + ResponseMessage.TECHNICAL_PROBLEMS;
			response.sendRedirect(url);

		} catch (ValidatorException message) {
			mes = message.getMessage();
			url = Transition.PAGE_REGISTRATION;
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

	private UserInfo createUserInfo(HttpServletRequest request) {

		String login = request.getParameter(RequestParameter.LOGIN);
		String password = request.getParameter(RequestParameter.PASSWORD);
		String email = request.getParameter(RequestParameter.EMAIL);
		String role = request.getParameter(RequestParameter.ROLE);
		String name = request.getParameter(RequestParameter.NAME);
		String surname = request.getParameter(RequestParameter.SURNAME);

		UserInfo userInfo = new UserInfo();

		if (!InitialCheck.checkString(login)) {
			return null;
		}
		userInfo.setLogin(login);
		if (!InitialCheck.checkString(password)) {
			return null;
		}
		userInfo.setPassword(password);
		if (!InitialCheck.checkString(email)) {
			return null;
		}
		userInfo.setEmail(email);
		if (!InitialCheck.checkString(role)) {
			return null;
		}
		userInfo.setRole(role);
		if (!InitialCheck.checkString(name)) {
			return null;
		}
		userInfo.setName(name);
		if (!InitialCheck.checkString(surname)) {
			return null;
		}
		userInfo.setSurname(surname);

		return userInfo;
	}

}
