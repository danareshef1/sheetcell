package sheet.coordinate;

import sheet.Layout;
import sheet.Sheet;

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

    public static int[] cellIdToRowCol(String cellId) {
        if (cellId == null || cellId.length() < 2) {
            throw new IllegalArgumentException("Invalid cell ID: " + cellId);
        }

        // Extract column part and row part
        String colPart = cellId.replaceAll("\\d", ""); // Extracts letters
        String rowPart = cellId.replaceAll("\\D", ""); // Extracts digits

        // Convert column part to zero-based index
        //int colIndex = colPart.chars().map(c -> c - 'A').sum();
        int colIndex = colPart.chars().reduce(0, (sum, c) -> sum * 26 + (c - 'A' + 1)) - 1;
        int rowIndex = Integer.parseInt(rowPart) - 1; // Convert 1-based to 0-based

        return new int[]{rowIndex, colIndex};
    }

    public static void coordinateValidator(String cellId, Layout sheetSize) {
        int[] cellIdToRowCol = cellIdToRowCol(cellId);
        if (cellIdToRowCol[0] <sheetSize.getMinimumRows() || cellIdToRowCol[0] > sheetSize.getNumRows()) {
            throw new IllegalArgumentException("Row number is out of sheet range. The range is:" + sheetSize.getMinimumRows() +
                    " to " + sheetSize.getNumRows() + ",and you entered " + cellId);
        }
        if (cellIdToRowCol[1] < sheetSize.getMaximumCols() || cellIdToRowCol[1] > sheetSize.getNumCols()) {
            throw new IllegalArgumentException("Column number is out of sheet range. The range is:" + sheetSize.getMinimumCols() +
                    " to " + sheetSize.getNumCols() + ",and you entered " + cellId);
        }
    }
}

