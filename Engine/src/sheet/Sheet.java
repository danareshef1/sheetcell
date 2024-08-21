package sheet;

import expression.Expression;
import sheet.cell.Cell;
import sheet.coordinate.Coordinate;

import java.util.Map;

public interface Sheet {
    int getVersion();
    Cell getCell(int row, int column);
    void setCell(int row, int column, String value);
    String getName();
    Layout getSheetSize();
    Map<Coordinate,Cell> getActiveCells();
    SheetImpl getInstance();
}
