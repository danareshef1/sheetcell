package sheet;

import expression.Expression;
import sheet.cell.Cell;
import sheet.coordinate.Coordinate;

import java.io.Serializable;
import java.util.Map;

public interface Sheet extends SheetReadActions, SheetUpdateActions, Serializable {
    int getCountChangedCells();
}
