//package menu;
//
//import sheet.cellSize.CellSize;
//import sheet.Layout;
//import sheet.SheetImpl;
//
//public class SetSheet {
//    private static SheetImpl sheet;
//
//    public SetSheet() {
//        sheet = setSheet();
//    }
//
//    public static SheetImpl setSheet() {
//        CellSize cellSize = new CellSize(9, 1); // Column width of 10 , row height of 3
//        Layout layout = new Layout(5, 5, cellSize); // 5 rows and 5 columns
//
//        sheet = new SheetImpl("MySheet", layout);
//        sheet.setCell(1, 1, "{PLUS, {minus, 2, 1}, 3}");
//       // sheet.setCell(2, 2, "{ref, B2}");
//
//        return sheet;
//    }
//}
//
