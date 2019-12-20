package by.htp.ts.controller.command.goto_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.command.constant.PageAddress;

public class GoToAuthorizationPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher disp = request.getRequestDispatcher(PageAddress.PAGE_AUTHORIZATION);
		disp.forward(request, response);
	}

}
