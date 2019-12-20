package by.htp.ts.controller.command.concrete_impl;

import java.io.IOException;

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

public class CreateTest implements Command {

	private final String TEST_SAVE_TRUE = "Test created successfully";
	private final String TEST_SAVE_FALSE = "A test with the same name already exists, try again";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter(RequestParameter.TITLE);
		String time = request.getParameter(RequestParameter.TIME);

		ServiceProvider provider = ServiceProvider.getInstance();
		TestService testService = provider.getTestService();

		try {

			if (testService.createTest(title, time)) {
				response.sendRedirect(RedirectConstant.REDIRECT_TEACHER_MESSAGE + TEST_SAVE_TRUE);
			} else {
				response.sendRedirect(RedirectConstant.REDIRECT_TEACHER_MESSAGE + TEST_SAVE_FALSE);
			}

		} catch (ValidatorException message) {
			response.sendRedirect(RedirectConstant.REDIRECT_TEACHER_MESSAGE + message.getMessage());
		} catch (ServiceException e) {
			// log
			response.sendRedirect(RedirectConstant.REDIRECT_ERROR + OtherConstant.TECHNICAL_PROBLEMS);
		}

	}

}
