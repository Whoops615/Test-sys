package by.htp.ts.controller.command.concrete_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.controller.command.Command;


public class Localization implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession s = request.getSession(true);
		s.setAttribute("local", request.getParameter("local"));
		
		String gotoRequest = (String) s.getAttribute("goto_request");
		
		if(gotoRequest.endsWith(".jsp")) {
			request.getRequestDispatcher(gotoRequest).forward(request, response);
		} else {
			response.sendRedirect(gotoRequest);
		}
		
		

	}

}
