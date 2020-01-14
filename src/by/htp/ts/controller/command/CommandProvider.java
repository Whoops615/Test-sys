package by.htp.ts.controller.command;

import java.util.HashMap;
import java.util.Map;

import by.htp.ts.controller.command.goto_impl.GoToAppointmentTestPage;
import by.htp.ts.controller.command.goto_impl.GoToAuthorizationPage;
import by.htp.ts.controller.command.goto_impl.GoToCreateQuestionPage;
import by.htp.ts.controller.command.goto_impl.GoToCreateTestPage;
import by.htp.ts.controller.command.goto_impl.GoToErrorPage;
import by.htp.ts.controller.command.goto_impl.GoToIndexPage;
import by.htp.ts.controller.command.goto_impl.GoToRegistrationPage;
import by.htp.ts.controller.command.goto_impl.GoToStudentPage;
import by.htp.ts.controller.command.goto_impl.GoToTeacherPage;
import by.htp.ts.controller.command.test_impl.AppointmentTest;
import by.htp.ts.controller.command.test_impl.CreateTest;
import by.htp.ts.controller.command.test_impl.FindAvailableTest;
import by.htp.ts.controller.command.test_impl.NextQuestion;
import by.htp.ts.controller.command.test_impl.PassTest;
import by.htp.ts.controller.command.test_impl.SaveQuestion;
import by.htp.ts.controller.command.test_impl.StartTest;
import by.htp.ts.controller.command.user_impl.Authorization;
import by.htp.ts.controller.command.user_impl.Localization;
import by.htp.ts.controller.command.user_impl.LogOut;
import by.htp.ts.controller.command.user_impl.Registration;

public class CommandProvider {

	private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();

	public CommandProvider() {
		commands.put(CommandName.AUTHORIZATION, new Authorization());
		commands.put(CommandName.REGISTRATION, new Registration());
		commands.put(CommandName.LOCALIZATION, new Localization());
		commands.put(CommandName.LOGOUT, new LogOut());
		commands.put(CommandName.GO_TO_ERROR_PAGE, new GoToErrorPage());
		commands.put(CommandName.GO_TO_INDEX_PAGE, new GoToIndexPage());
		commands.put(CommandName.GO_TO_TEACHER_PAGE, new GoToTeacherPage());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());

		commands.put(CommandName.CREATE_TEST, new CreateTest());


		commands.put(CommandName.GO_TO_AUTHORIZATION_PAGE, new GoToAuthorizationPage());
		commands.put(CommandName.GO_TO_STUDENT_PAGE, new GoToStudentPage());
		commands.put(CommandName.GO_TO_CREATE_TEST_PAGE, new GoToCreateTestPage());
		commands.put(CommandName.GO_TO_CREATE_QUESTION_PAGE, new GoToCreateQuestionPage());
		commands.put(CommandName.GO_TO_APPOINTMENT_TEST_PAGE, new GoToAppointmentTestPage());
		
		commands.put(CommandName.SAVE_QUESTION, new SaveQuestion());
		commands.put(CommandName.APPOINTMENT_TEST, new AppointmentTest());
		commands.put(CommandName.FIND_AVAILABLE_TEST, new FindAvailableTest());
		commands.put(CommandName.PASS_TEST, new PassTest());
		commands.put(CommandName.START_TEST, new StartTest());
		commands.put(CommandName.NEXT_QUESTION, new NextQuestion());
		

	}

	public Command getCommand(String name) {

		CommandName commandName;
		commandName = CommandName.valueOf(name.toUpperCase());
		return commands.get(commandName);
	}

}
