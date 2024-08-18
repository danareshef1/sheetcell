package sheet;

import expression.Expression;
import sheet.cell.Cell;

public interface Sheet {
    int getVersion();
    Cell getCell(int row, int column);
    void setCell(int row, int column, String value);
    String getName();
}