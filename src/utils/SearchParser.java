package utils;

import java.util.ArrayList;

public class SearchParser {

    /**
     * Parses the search input string using only ArrayLists.
     * 
     * If input contains key-value pairs (e.g. "ID:S001, Name:Teoh"):
     * Returns: ["ID", "S001", "Name", "Teoh"] (alternating key, value)
     * 
     * If input is generic (e.g. "Teoh"):
     * Returns: ["GENERIC", "Teoh"]
     */
    public static ArrayList<String> parseSearch(String text) {
        ArrayList<String> parsedArray = new ArrayList<>();

        if (text == null || text.trim().isEmpty()) {
            return parsedArray;
        }

        // Split by comma
        String[] splitList = text.split(",");

        for (String item : splitList) {
            String trimmedItem = item.trim();
            if (trimmedItem.isEmpty()) {
                continue;
            }

            if (trimmedItem.contains(":")) {
                // Split into key and value (e.g. "ID:S001" -> ["ID", "S001"])
                String[] fieldAttr = trimmedItem.split(":");
                String key = fieldAttr[0].trim();
                String value = fieldAttr.length > 1 ? fieldAttr[1].trim() : "";

                if (!key.isEmpty() && !value.isEmpty()) {
                    parsedArray.add(key);
                    parsedArray.add(value);
                }
            } else {
                // Generic search term
                parsedArray.add("GENERIC");
                parsedArray.add(trimmedItem);
            }
        }

        return parsedArray;
    }
}
