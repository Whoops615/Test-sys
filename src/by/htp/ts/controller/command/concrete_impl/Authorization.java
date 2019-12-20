package by.htp.ts.controller.command.concrete_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.User;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.command.constant.OtherConstant;
import by.htp.ts.controller.command.constant.RedirectConstant;
import by.htp.ts.controller.command.constant.RequestParameter;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.UserService;
import by.htp.ts.service.validator.ValidatorException;

public class Authorization implements Command {

	private final String NO_SUCH_USER = "No such user, try again or register";
	private final String HELLO_MESSAGE = "Hello, you are successfully logged in.";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter(RequestParameter.LOGIN);
		String password = request.getParameter(RequestParameter.PASSWORD);

		ServiceProvider provider = ServiceProvider.getInstance();
		UserService userService = provider.getUserService();

		User user;

		try {
			user = userService.authorization(login, password);

			if (user == null) {
				response.sendRedirect(RedirectConstant.REDIRECT_AUTHORIZATION_ERROR + NO_SUCH_USER);
				return;
			}

			HttpSession session = request.getSession(true);
			session.setAttribute("user", user);	
			String gotoRequest = checkRole(user);
			
			session.setAttribute("goto_request", gotoRequest);
			response.sendRedirect(gotoRequest);

		} catch (ServiceException e) {
			// log
			response.sendRedirect(RedirectConstant.REDIRECT_ERROR + OtherConstant.TECHNICAL_PROBLEMS);
		} catch (ValidatorException message) {
			response.sendRedirect(RedirectConstant.REDIRECT_AUTHORIZATION_ERROR + message.getMessage());
		}

	}
	
	private String checkRole(User user) {
		
		String role = user.getRole();
		
		switch(role) {
		case "teacher":
			return RedirectConstant.REDIRECT_TEACHER_MESSAGE + HELLO_MESSAGE;
		}
		
		return null;
	}
	

}
