package by.htp.ts.controller.command.concrete_impl;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.controller.command.Command;

import by.htp.ts.controller.command.constant.RedirectConstant;

public class TeacherAction implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String action = request.getParameter("action");
		String gotoRequest = RedirectConstant.REDIRECT_TEACHER_ACTION+action;
		HttpSession session = request.getSession(true);
		session.setAttribute("goto_request", gotoRequest);
		
		response.sendRedirect(gotoRequest);
		
		
		
		
	}

}
