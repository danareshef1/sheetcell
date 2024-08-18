package sheet;

public class CellSize {
    private final int colWidth;
    private final int rowHeight;

    public CellSize(int colWidth, int rowHeight) {
        this.colWidth = colWidth;
        this.rowHeight = rowHeight;
    }
    public int getColWidth() {
        return colWidth;
    }
    public int getRowHeight() {
        return rowHeight;
    }
}
