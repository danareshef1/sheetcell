package sheet.coordinate;

import java.util.HashMap;
import java.util.Map;

public class CoordinateFactory {

    private static Map<String, Coordinate> cachedCoordinates = new HashMap<>();

    public static Coordinate createCoordinate(int row, int column) {

        String key = row + ":" + column;
        if (cachedCoordinates.containsKey(key)) {
            return cachedCoordinates.get(key);
        }

        CoordinateImpl coordinate = new CoordinateImpl(row, column);
        cachedCoordinates.put(key, coordinate);

        return coordinate;
    }

    public static Coordinate from(String trim) {

        try {
            // Split the input into column and row parts
            String columnPart = trim.replaceAll("\\d", "");  // Extracts the column part (letters)
            String rowPart = trim.replaceAll("\\D", "");     // Extracts the row part (digits)

            // Convert the row part to an integer
            int row = Integer.parseInt(rowPart);

            // Convert the column part to a zero-based index
            int column = columnPart.chars().reduce(0, (sum, c) -> sum * 26 + (c - 'A' + 1)) - 1;

            return createCoordinate(row, column);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

