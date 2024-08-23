package sheet;

import sheet.cell.Cell;
import sheet.coordinate.Coordinate;

import java.util.Map;

public interface SheetReadActions {
    int getVersion();
    Cell getCell(int row, int column);
    String getName();
    Layout getSheetSize();
    Map<Coordinate,Cell> getActiveCells();
    SheetImpl getInstance();
}
