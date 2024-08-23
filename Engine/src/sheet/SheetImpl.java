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
    public Map<Coordinate,Cell> getActiveCells() {
        return activeCells;
    }

    @Override
    public SheetImpl getInstance(){
        return this;
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

    @Override
    public Layout getSheetSize()
    {
        return size;
    }

    @Override
    public Cell getCell(int row, int column) {
        sheetBoundsCheck(row, column);

        if(activeCells.get(CoordinateFactory.createCoordinate(row, column)) == null){
            setCell(row, column, null);
        }
        return activeCells.get(CoordinateFactory.createCoordinate(row, column));
    }

    public void sheetBoundsCheck(int row, int column) {
        if (row > size.getNumRows() || column > size.getNumCols()) {
            throw new IllegalArgumentException("Those coordinates are out of the sheet's bounds.");
        }
    }

    @Override
    public void setCell(int row, int column, String value) {
        sheetBoundsCheck(row, column);

        Coordinate coordinate = CoordinateFactory.createCoordinate(row, column);
        Cell cell = activeCells.get(coordinate);
        if (cell == null) {
            cell = new CellImpl(row, column, value);
            activeCells.put(coordinate, cell);
        }
        cell.setCellOriginalValue(value);
        cell.updateVersion();
    }
}

