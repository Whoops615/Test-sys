package by.htp.ts.controller.command.util;

public class InitialCheck {

	public static boolean checkString(String data) {

		if (data == null || data.trim().isEmpty()) {
			return false;
		}

		return true;
	}

	public static boolean checkAnswer(String answer, String result) {

		if (!checkString(answer)) {
			return false;
		}

		if (!checkString(result)) {
			return false;
		}

		return true;
	}

}
