package sheet.layout;

import sheet.cellSize.CellSize;

public class LayoutImpl implements Layout {
    private final int numRows;
    private final int numCols;
    private final CellSize cellSize;
    private final int minimumRows = 1;
    private final int minimumCols = 1;
    private final int maximumRows = 50;
    private final int maximumCols = 20;

    public LayoutImpl(int numRows, int numCols, CellSize cellSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.cellSize = cellSize;
    }

    @Override
    public int getNumRows() {
        return numRows;
    }
    @Override
    public int getNumCols() {
        return numCols;
    }
    @Override
    public CellSize getCellSize() {
        return cellSize;
    }
    @Override
    public int getMinimumRows() {
        return minimumRows;
    }
    @Override
    public int getMinimumCols() {
        return minimumCols;
    }
    @Override
    public int getMaximumRows() {
        return maximumRows;
    }
    @Override
    public int getMaximumCols() {
        return maximumCols;
    }
}

