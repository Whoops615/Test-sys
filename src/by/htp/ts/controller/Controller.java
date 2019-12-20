package by.htp.ts.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.ts.controller.command.Command;
import by.htp.ts.controller.command.CommandProvider;
import by.htp.ts.dao.connect_pool.ConnectionPool;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String COMMAND_NAME = "command";
	private final CommandProvider provider = new CommandProvider();

	public Controller() {
		super();
		

	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		@SuppressWarnings("unused")
		ConnectionPool cp = ConnectionPool.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Command command;
		String commandName = request.getParameter(COMMAND_NAME);
		command = provider.getCommand(commandName);
		command.execute(request, response);
	}
	@Override
	public void destroy() {		
		super.destroy();
		ConnectionPool cp = ConnectionPool.getInstance();
		cp.dispose();
		
	}
}
