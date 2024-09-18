package sheet.layout;

import sheet.cellSize.CellSize;
import java.io.Serializable;

public interface Layout extends Serializable {
    int getNumRows();
    int getNumCols();
    CellSize getCellSize();
    int getMinimumRows();
    int getMinimumCols();
    int getMaximumRows();
    int getMaximumCols();
}
