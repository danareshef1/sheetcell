package sheet.cellSize;

public class CellSizeImpl implements CellSize {
    private final int colWidth;
    private final int rowHeight;

    public CellSizeImpl(int colWidth, int rowHeight) {
        this.colWidth = colWidth;
        this.rowHeight = rowHeight;
    }
    @Override
    public int getColWidth() {
        return colWidth;
    }
    @Override
    public int getRowHeight() {
        return rowHeight;
    }
}
