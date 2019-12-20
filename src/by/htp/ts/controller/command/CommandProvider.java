package by.htp.ts.controller.command;

import java.util.HashMap;
import java.util.Map;

import by.htp.ts.controller.command.concrete_impl.Authorization;
import by.htp.ts.controller.command.concrete_impl.CreateTest;
import by.htp.ts.controller.command.concrete_impl.Localization;
import by.htp.ts.controller.command.concrete_impl.LogOut;
import by.htp.ts.controller.command.concrete_impl.Registration;
import by.htp.ts.controller.command.concrete_impl.SaveQuestion;
import by.htp.ts.controller.command.concrete_impl.TeacherAction;

import by.htp.ts.controller.command.goto_impl.GoToAuthorizationPage;
import by.htp.ts.controller.command.goto_impl.GoToErrorPage;
import by.htp.ts.controller.command.goto_impl.GoToIndexPage;
import by.htp.ts.controller.command.goto_impl.GoToRegistrationPage;
import by.htp.ts.controller.command.goto_impl.GoToTeacherPage;

public class CommandProvider {

	private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();

	public CommandProvider() {
		commands.put(CommandName.AUTHORIZATION, new Authorization());
		commands.put(CommandName.REGISTRATION, new Registration());
		commands.put(CommandName.LOCALIZATION, new Localization());
		commands.put(CommandName.LOGOUT, new LogOut());
		
		commands.put(CommandName.GO_TO_AUTHORIZATION_PAGE, new GoToAuthorizationPage());
		commands.put(CommandName.GO_TO_ERROR_PAGE, new GoToErrorPage());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
		commands.put(CommandName.GO_TO_INDEX_PAGE, new GoToIndexPage());
		commands.put(CommandName.GO_TO_TEACHER_PAGE, new GoToTeacherPage());
		commands.put(CommandName.TEACHER_COMMAND, new TeacherAction());
		
		commands.put(CommandName.CREATE_TEST, new CreateTest());
		commands.put(CommandName.SAVE_QUESTION, new SaveQuestion());
		
	}

	public Command getCommand(String name) {

		CommandName commandName;
		commandName = CommandName.valueOf(name.toUpperCase());
		return commands.get(commandName);
	}

}
