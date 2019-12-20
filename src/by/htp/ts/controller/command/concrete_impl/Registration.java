package by.htp.ts.controller.command.concrete_impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.User;
import by.htp.ts.bean.UserInfo;
import by.htp.ts.bean.UserParameter;
import by.htp.ts.bean.creator.Creator;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.command.constant.OtherConstant;

import by.htp.ts.controller.command.constant.RedirectConstant;
import by.htp.ts.controller.command.constant.RequestParameter;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.UserService;
import by.htp.ts.service.validator.ValidatorException;

public class Registration implements Command {

	private final String FAIL = "A user with that username already exists!";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter(RequestParameter.LOGIN);
		String password = request.getParameter(RequestParameter.PASSWORD);
		String email = request.getParameter(RequestParameter.EMAIL);
		String role = request.getParameter(RequestParameter.ROLE);
		String name = request.getParameter(RequestParameter.NAME);
		String surname = request.getParameter(RequestParameter.SURNAME);

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(UserParameter.LOGIN.name(), login);
		parameter.put(UserParameter.PASSWORD.name(), password);
		parameter.put(UserParameter.EMAIL.name(), email);
		parameter.put(UserParameter.ROLE.name(), role);
		parameter.put(UserParameter.NAME.name(), name);
		parameter.put(UserParameter.SURNAME.name(), surname);

		ServiceProvider provider = ServiceProvider.getInstance();
		Creator cre = Creator.getInstance();
		UserService service = provider.getUserService();
		UserInfo userInfo;
		User user;
		userInfo = cre.createUserInfo(parameter);

		try {

			if (service.registration(userInfo)) {

				user = service.authorization(login, password);

				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				response.sendRedirect(RedirectConstant.REDIRECT_INDEX);

			} else {
				response.sendRedirect(RedirectConstant.REDIRECT_REGISTRATION_ERROR + FAIL);
			}

		} catch (ServiceException e) {
			// log
			response.sendRedirect(RedirectConstant.REDIRECT_ERROR + OtherConstant.TECHNICAL_PROBLEMS);
		} catch (ValidatorException message) {
			response.sendRedirect(RedirectConstant.REDIRECT_REGISTRATION_ERROR + message.getMessage());
		}

	}

}
