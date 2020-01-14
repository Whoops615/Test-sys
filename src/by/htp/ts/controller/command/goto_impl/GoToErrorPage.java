package by.htp.ts.controller.command.goto_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.RequestInfo;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.constant.Transition;

public class GoToErrorPage implements Command {

	private RequestInfo requestInfo = new RequestInfo();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String url = Transition.PAGE_ERROR;
		HttpSession session = request.getSession(true);
		requestInfo.setUrl(url);
		session.setAttribute("requestInfo", requestInfo);

		RequestDispatcher disp = request.getRequestDispatcher(url);
		disp.forward(request, response);

	}

}
