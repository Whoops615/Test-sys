package by.htp.ts.service.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	private final static Pattern PATTERN_LOGIN = Pattern.compile("[a-zA-Z|0-9|\\u005F|-]{4,20}");
	private final static Pattern PATTERN_PASSWORD = Pattern.compile("[a-zA-Z|0-9]{4,20}");
	private final static Pattern PATTERN_EMAIL = Pattern
			.compile("[a-zA-Z|0-9|\u005F|-]+@{1}[a-z]+\u002E{1}((by)|(ru)|(com)|(org)|(net)){1}");
	private final static Pattern PATTERN_NAME_OR_SURNAME = Pattern.compile("[a-zA-Z]{1,30}");

	private final static String ROLE_ADMIN = "admin";
	private final static String ROLE_TEACHER = "teacher";
	private final static String ROLE_STUDENT = "student";

	private final static Pattern PATTERN_TEST_TITLE = Pattern.compile("[a-zA-Z|0-9]{1,30}");

	public static boolean validLogin(String login) {

		Matcher m = PATTERN_LOGIN.matcher(login);

		if (!m.matches()) {
			return false;
		}

		return true;
	}

	public static boolean validPassword(String password) {

		Matcher m = PATTERN_PASSWORD.matcher(password);

		if (!m.matches()) {
			return false;
		}
		return true;
	}

	public static boolean validRole(String role) {

		switch (role) {
		case ROLE_ADMIN:
			return true;
		case ROLE_TEACHER:
			return true;
		case ROLE_STUDENT:
			return true;
		default:
			return false;
		}

	}

	public static boolean validEmail(String email) {

		Matcher m = PATTERN_EMAIL.matcher(email);

		if (!m.matches()) {
			return false;
		}

		return true;
	}

	public static boolean validNameOrSurname(String name) {

		Matcher m = PATTERN_NAME_OR_SURNAME.matcher(name);

		if (!m.matches()) {
			return false;
		}

		return true;
	}

	public static boolean validTestTitle(String title) {

		Matcher m = PATTERN_TEST_TITLE.matcher(title);

		if (!m.matches()) {
			return false;
		}
		return true;
	}

	public static boolean validTestTime(int hour, int minute) {

		if (hour > 23 || hour < 0 || minute > 59 || minute < 0) {
			return false;
		}

		return true;
	}

	public static boolean validTextQuestion(String text) {

		String str = text.trim();
		if (!str.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean validAnswer(String answer, String result) {

		if (!answer.isEmpty()) {
			
			switch (result) {
			case "true":
				return true;
			case "false":
				return true;
			default:
				return false;
			}
		}

		return false;
	}
	


}
