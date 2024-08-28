package sheet;

import sheet.cell.Cell;
import sheet.coordinate.Coordinate;

public interface SheetUpdateActions {
    void setCell(int row, int column, String value);
    Sheet updateCellValueAndCalculate(int row, int column, String value, boolean first);
    void incrementVersion();
    void addCell(Cell cell);
    SheetImpl copySheet();
}