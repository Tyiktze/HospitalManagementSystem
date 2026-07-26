package utils;

public class InputValidator {
	public static Integer parseInteger(String value) {
		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			return null;
		}
	}
}
