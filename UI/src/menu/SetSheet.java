package menu;

import sheet.CellSize;
import sheet.Layout;
import sheet.SheetImpl;

public class SetSheet {
    private static SheetImpl sheet;

    public SetSheet() {
        sheet = setSheet();
    }

    public static SheetImpl setSheet() {
        CellSize cellSize = new CellSize(9, 1); // Column width of 10 characters, row height of 3 characters
        Layout layout = new Layout(5, 5, cellSize); // 5 rows and 5 columns

        // Create the sheet with the specified version, name, cells, and layout
        sheet = new SheetImpl("MySheet", layout);
        sheet.setCell(1, 1, "{SUB, {CONCAT, A, bcdE}, 0, 3}");

        return sheet;
    }
}

