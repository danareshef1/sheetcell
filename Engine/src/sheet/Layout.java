package sheet;

public class Layout {
    private final int numRows;
    private final int numCols;
    private final CellSize cellSize;

    public Layout(int numRows, int numCols, CellSize cellSize) {
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

    public CellSize getCellSize() {
        return cellSize;
    }
}
