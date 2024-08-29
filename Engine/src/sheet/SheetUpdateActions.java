package sheet;

import sheet.cell.Cell;

public interface SheetUpdateActions {
    Sheet updateCellValueAndCalculate(int row, int column, String value, boolean first);
    void incrementVersion();
    void addCell(Cell cell);
    SheetImpl copySheet();
}