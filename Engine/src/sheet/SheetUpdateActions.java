package sheet;

import sheet.cell.Cell;
import sheet.coordinate.Coordinate;

public interface SheetUpdateActions {
    void setCell(int row, int column, String value);
    Sheet updateCellValueAndCalculate(int row, int column, String value);
    void incrementVersion();
    void addCell(Coordinate coordinate, Cell cell);
}
