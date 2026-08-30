package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAndTime {

    // Define a clean format: MMM dd, yyyy | hh:mm a
    private static  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy | hh:mm a");

    public static String getFormattedDateTime() {
        return LocalDateTime.now().format(formatter);
    }
}

