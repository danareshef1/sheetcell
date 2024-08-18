package sheet;

import sheet.cell.Cell;
import sheet.cell.CellImpl;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;

import java.util.HashMap;
import java.util.Map;

public class SheetImpl implements Sheet {
    private Version version;
    private final String name;
    private Map<Coordinate,Cell> activeCells;
    private final Layout size;

    public SheetImpl(String name, Layout size) {
        this.version = new Version();
        this.name = name;
        this.size = size;
        this.activeCells = new HashMap<>();
    }

    @Override
    public int getVersion() {
        return version.getVersionNumber();
    }

    public void incrementVersion() {
        version.incrementVersion();
    }

    public int getCellChangedNumber() {
        return version.getCellsChanged();
    }

    public void incrementCellChanged() {
        version.incrementCellChanged();
    }

    @Override
    public String getName() {
        return name;
    }

    public Layout getSheetSize()
    {
        return size;
    }

    @Override
    public Cell getCell(int row, int column) {
        if(activeCells.get(CoordinateFactory.createCoordinate(row, column)) == null){
            setCell(row, column, null);
        }
        return activeCells.get(CoordinateFactory.createCoordinate(row, column));
    }

    @Override
    public void setCell(int row, int column, String value) {
        Coordinate coordinate = CoordinateFactory.createCoordinate(row, column);
        Cell cell = activeCells.get(coordinate);
        if (cell == null) {
            cell = new CellImpl(row, column, value);
            activeCells.put(coordinate, cell);
        }
        cell.setCellOriginalValue(value);
    }

//    // Convert cell ID to row and column indices
//    private int[] cellIdToRowCol(String cellId) {
//        if (cellId == null || cellId.length() < 2) {
//            throw new IllegalArgumentException("Invalid cell ID");
//        }
//
//        // Extract column part and row part
//        String colPart = cellId.replaceAll("\\d", ""); // Extracts letters
//        String rowPart = cellId.replaceAll("\\D", ""); // Extracts digits
//
//        // Convert column part to zero-based index
//        int colIndex = colPart.chars().map(c -> c - 'A').sum();
//        int rowIndex = Integer.parseInt(rowPart) - 1; // Convert 1-based to 0-based
//
//        return new int[]{rowIndex, colIndex};
//    }
//
//    // Get the cell content at specific cell ID
//    public String getCellContent(String cellId) {
//        int[] indices = cellIdToRowCol(cellId);
//        int row = indices[0];
//        int col = indices[1];
//
//        if (row >= 0 && row < size.getNumRows() && col >= 0 && col < size.getNumCols()) {
//            CellImpl<?> cell = cells[row][col];
//            if (cell != null) {
//                Object content = cell.getContent();
//                return content != null ? content.toString() : "";
//            }
//        }
//        return "";
//    }
}

