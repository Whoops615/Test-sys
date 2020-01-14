package by.htp.ts.controller.command.user_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.RequestInfo;
import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.constant.Transition;

public class Localization implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.setAttribute("local", request.getParameter("local"));

		RequestInfo requestInfo = (RequestInfo) session.getAttribute("requestInfo");

		if (requestInfo == null) {
			RequestDispatcher disp = request.getRequestDispatcher(Transition.PAGE_INDEX);
			disp.forward(request, response);
			return;
		}

		String url = requestInfo.getUrl();

		if (url.endsWith(".jsp")) {
			String message = requestInfo.getMessage();
			if (message != null) {
				request.setAttribute("message", message);
			}
			request.getRequestDispatcher(url).forward(request, response);

		} else {
			response.sendRedirect(url);
		}

	}

}
