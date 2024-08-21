package menu.menuItems.selectionTwo;

import fromEngine.CellDTO;
import fromEngine.SheetDTO;

public class SheetPrinter {

    public static void printSheet(SheetDTO sheet) {
        System.out.println("Version: " + sheet.getVersion());
        System.out.println("Sheet Name: " + sheet.getName());
        System.out.print("    |");
        for (int col = 0; col < sheet.getSheetSize().getNumCols(); col++) {
            char columnLetter = (char)('A' + col);
            System.out.printf(" %-" + sheet.getSheetSize().getCellSize().getColWidth() + "s |", columnLetter);
        }
        System.out.println();
        for (int row = 0; row < sheet.getSheetSize().getNumRows(); row++) {
            System.out.printf("%02d  |", row + 1);
            for (int col = 0; col < sheet.getSheetSize().getNumCols(); col++) {
                CellDTO cell = sheet.getCell(row, col);
                String cellValue = cell != null && cell.getContent() != null
                        ? cell.getContent() : "";
                System.out.printf(" %-" + sheet.getSheetSize().getCellSize().getColWidth() + "s |", cellValue);
            }
            System.out.println();
            for (int h = 1; h < sheet.getSheetSize().getCellSize().getRowHeight(); h++) {
                System.out.print("    |");
                for (int col = 0; col < sheet.getSheetSize().getNumCols(); col++) {
                    // Print empty space to match the row height
                    System.out.printf(" %-" + sheet.getSheetSize().getCellSize().getColWidth() + "s |", "");
                }
                System.out.println();
            }
        }
    }
}
