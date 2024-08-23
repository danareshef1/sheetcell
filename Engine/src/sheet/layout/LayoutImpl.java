package sheet.layout;

import sheet.cellSize.CellSizeImpl;

import java.io.Serializable;

public class LayoutImpl implements Layout {
    private final int numRows;
    private final int numCols;
    private final CellSizeImpl cellSize;
    private final int minimumRows = 1;
    private final int minimumCols = 1;
    private final int maximumRows = 50;
    private final int maximumCols = 20;

    public LayoutImpl(int numRows, int numCols, CellSizeImpl cellSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.cellSize = cellSize;
    }
    public int getNumRows() {
        return numRows;
    }
    public int getNumCols() {
        return numCols;
    }

    public CellSizeImpl getCellSize() {
        return cellSize;
    }

    public int getMinimumRows() {
        return minimumRows;
    }
    public int getMinimumCols() {
        return minimumCols;
    }
    public int getMaximumRows() {
        return maximumRows;
    }
    public int getMaximumCols() {
        return maximumCols;
    }
}

