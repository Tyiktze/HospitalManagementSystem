package utils;

public class InputValidator {
	public static Integer parseInteger(String value) {
		if (value == null){
			return null;
		}

		String trimmedValue = value.trim();//delete all the space (front & behind)
		if(trimmedValue.isEmpty()){
			return null;
		}

		try {
			return Integer.parseInt(trimmedValue);
		}
		catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static boolean isPositive(Integer value) {
	    return value != null && value > 0;
	}
	
	public static boolean isNonNegative(Integer value) {
	    return value != null && value >= 0;
	}

}
