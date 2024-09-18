package sheet;

import sheet.cell.Cell;

public interface SheetUpdateActions {
    Sheet updateCellValueAndCalculate(int row, int column, String value, boolean first);
    void incrementVersion();
    SheetImpl copySheet();
}