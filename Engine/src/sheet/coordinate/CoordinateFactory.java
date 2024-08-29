package sheet.coordinate;

import sheet.layout.Layout;
import java.util.HashMap;
import java.util.Map;
import static sheet.cell.CellImpl.columnToString;

public class CoordinateFactory {

    private static final Map<String, Coordinate> cachedCoordinates = new HashMap<>();

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

        // Extract column part (letters) and row part (digits)
        String colPart = cellId.replaceAll("\\d", "").trim(); // Extracts letters and trims spaces
        String rowPart = cellId.replaceAll("\\D", "").trim(); // Extracts digits and trims spaces

        try {
            // Convert column part to zero-based index
            int colIndex = colPart.chars().reduce(0, (sum, c) -> sum * 26 + (c - 'A' + 1)) - 1;

            // Convert row part to zero-based index
            int rowIndex = Integer.parseInt(rowPart) - 1;

            return createCoordinate(rowIndex, colIndex);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cell ID: " + cellId, e);
        }
    }

    public static void coordinateValidator(String cellId, Layout sheetSize) {
        Coordinate coordinate = cellIdToRowCol(cellId);
        if (coordinate.getRow() < (sheetSize.getMinimumRows()-1) || coordinate.getRow() > (sheetSize.getNumRows()-1)) {
            throw new IllegalArgumentException("Row number is out of sheet range. The range is:" + sheetSize.getMinimumRows() +
                    " to " + sheetSize.getNumRows() + ",and you entered " + (coordinate.getRow()+1));
        }
        if (coordinate.getColumn() < (sheetSize.getMinimumCols()-1) || coordinate.getColumn() > (sheetSize.getNumCols()-1)) {
            throw new IllegalArgumentException("Column number is out of sheet range. The range is:" + columnToString(sheetSize.getMinimumCols()-1) +
                    " to " + columnToString(sheetSize.getNumCols()-1) + ",and you entered " + columnToString(coordinate.getColumn()));
        }
    }
}