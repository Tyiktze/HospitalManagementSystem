package utils;

import java.util.List;
import java.util.function.Function;

public class IDCalculator {

    public static <T> int calculateNextId(List<T> list, Function<T, String> idExtractor) {
        int maxId = 0;
        if (list == null) {
            return 1;
        }

        for (T item : list) {
            if (item == null) {
                continue;
            }
            String id = idExtractor.apply(item);
            if (id != null && id.matches("[A-Za-z]+(\\d+)")) {
                try {
                    int num = Integer.parseInt(id.replaceAll("\\D+", ""));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        return maxId + 1;
    }
}
