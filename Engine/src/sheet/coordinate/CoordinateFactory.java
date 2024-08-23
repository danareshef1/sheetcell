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

    public static Coordinate cellIdToRowCol(String cellId) {
        if (cellId == null || cellId.length() < 2) {
            throw new IllegalArgumentException("Invalid cell ID: " + cellId);
        }

        // Extract column part and row part
        String rowPart = cellId.replaceAll("\\D", ""); // Extracts digits
        String colPart = cellId.replaceAll("\\d", ""); // Extracts letters

        // Convert column part to zero-based index
        //int colIndex = colPart.chars().map(c -> c - 'A').sum();
        int colIndex = colPart.chars().reduce(0, (sum, c) -> sum * 26 + (c - 'A' + 1)) - 1;
        int rowIndex = Integer.parseInt(rowPart) - 1; // Convert 1-based to 0-based

        return createCoordinate(rowIndex, colIndex);
    }

    public static void coordinateValidator(String cellId, Layout sheetSize) {
        Coordinate coordinate = cellIdToRowCol(cellId);
        if (coordinate.getRow() < sheetSize.getMinimumRows()-1 || coordinate.getRow() > sheetSize.getNumRows()-1) {
            throw new IllegalArgumentException("Row number is out of sheet range. The range is:" + sheetSize.getMinimumRows() +
                    " to " + sheetSize.getNumRows() + ",and you entered " + cellId);
        }
        if (coordinate.getColumn() < sheetSize.getMinimumCols()-1 || coordinate.getColumn() > sheetSize.getNumCols()-1) {
            throw new IllegalArgumentException("Column number is out of sheet range. The range is:" + sheetSize.getMinimumCols() +
                    " to " + sheetSize.getNumCols() + ",and you entered " + cellId);
        }
    }
}

