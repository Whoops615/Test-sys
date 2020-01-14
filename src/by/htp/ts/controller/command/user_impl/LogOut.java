package by.htp.ts.controller.command.user_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.constant.Transition;

public class LogOut implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.invalidate();
		request.getRequestDispatcher(Transition.PAGE_INDEX).forward(request, response);

	}

}
