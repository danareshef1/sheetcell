package fromEngine;

import fromUI.CellUpdateDTO;
import sheet.layout.LayoutImpl;
import sheet.Sheet;
import sheet.cell.Cell;
import sheet.cell.CellImpl;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;

import java.util.HashMap;
import java.util.Map;

public class SheetDTO {
    private int version;
    private String name;
    private Map<Coordinate, CellDTO> activeCells;
    private LayoutImpl size;

    public SheetDTO(Sheet sheet) {
        this.name = sheet.getName();
        this.version = sheet.getVersion();
        this.size = sheet.getSheetSize();
        this.activeCells = convertCellsToDTO(sheet);
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
    public LayoutImpl getSheetSize() {
        return size;
    }
    public CellDTO getCell(int row, int col) {
        return activeCells.get(CoordinateFactory.createCoordinate(row, col));
    }

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

    public CellUpdateDTO updateCell(int row, int col, String newValue) {
        Coordinate coordinate = CoordinateFactory.createCoordinate(row, col);
        CellDTO cellDTO = activeCells.get(coordinate);

        if (cellDTO == null) {
            // Create a new CellDTO if the cell was empty
            Cell newCell = new CellImpl(row, col, newValue, 0, (Sheet)this);
            cellDTO = new CellDTO(newCell);
        }

        cellDTO.updateCell(newValue);
        this.activeCells.put(coordinate, cellDTO);
        incrementVersion();
        return new CellUpdateDTO(coordinate.toString(), newValue);
    }

    // Increment sheet version
    public void incrementVersion() {
        this.version++;
    }

    public void incrementCellChanged() {
        ////to do
    }
}
