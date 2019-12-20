package by.htp.ts.controller.command.concrete_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.command.constant.PageAddress;

public class LogOut implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		session.removeAttribute("user");
		request.getRequestDispatcher(PageAddress.PAGE_INDEX).forward(request, response);
		
	}

}
