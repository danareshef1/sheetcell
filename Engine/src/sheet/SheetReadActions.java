package sheet;

import sheet.cell.Cell;
import sheet.coordinate.Coordinate;
import sheet.layout.Layout;
import java.util.Map;

public interface SheetReadActions {
    int getVersion();
    Cell getCell(int row, int column);
    String getName();
    Layout getSheetSize();
    Map<Coordinate,Cell> getActiveCells();
    int getCountChangedCells();
    void setCurrentCalculatingCell(Cell currentCalculatingCell);
    Cell getCurrentCalculatingCell();
}
