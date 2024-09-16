package fromEngine;

import sheet.layout.Layout;
import sheet.Sheet;
import sheet.cell.Cell;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;

import java.util.HashMap;
import java.util.Map;

public class SheetDTO {
    private int version;
    private String name;
    private Map<Coordinate, CellDTO> activeCells;
    private Layout size;
    private int counterChangedCells;

    public SheetDTO(Sheet sheet) {
        this.name = sheet.getName();
        this.version = sheet.getVersion();
        this.size = sheet.getSheetSize();
        this.activeCells = convertCellsToDTO(sheet);
        this.counterChangedCells = sheet.getCountChangedCells();
    }

    public int getCounterChangedCells(){
        return counterChangedCells;
    }
    public int getVersion() {
        return version;
    }
    public String getName() {
        return name;
    }
    public Map<Coordinate, CellDTO> getActiveCells() {
        return activeCells;
    }
    public Layout getSheetSize() {
        return size;
    }

    // Returns a specific cell by its row and column
    public CellDTO getCell(int row, int col) {
        Coordinate coordinate = CoordinateFactory.createCoordinate(row, col);
        return activeCells.get(coordinate);
    }

    // Converts active cells in the sheet to CellDTOs
    private Map<Coordinate, CellDTO> convertCellsToDTO(Sheet sheet) {
        Map<Coordinate, CellDTO> cells = new HashMap<>();
        // Iterate over the active cells in the sheet and map them to CellDTOs
        for (Map.Entry<Coordinate, Cell> entry : sheet.getActiveCells().entrySet()) {
            cells.put(entry.getKey(), new CellDTO(entry.getValue()));
        }
        return cells;
    }

    public static SheetDTO createSheetDTO(Sheet sheet) {
        if (sheet == null) {
            return null;
        }
        return new SheetDTO(sheet);
    }
}