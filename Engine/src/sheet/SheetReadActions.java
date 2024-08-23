package sheet;

import sheet.cell.Cell;
import sheet.coordinate.Coordinate;
import sheet.layout.LayoutImpl;

import java.util.Map;

public interface SheetReadActions {
    int getVersion();
    Cell getCell(int row, int column);
    String getName();
    LayoutImpl getSheetSize();
    Map<Coordinate,Cell> getActiveCells();
}
