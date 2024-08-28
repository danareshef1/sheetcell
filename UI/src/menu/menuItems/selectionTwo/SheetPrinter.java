package menu.menuItems.selectionTwo;

import fromEngine.CellDTO;
import fromEngine.SheetDTO;

import java.text.DecimalFormat;

public class SheetPrinter {

    // DecimalFormat for numerical values
    private static final DecimalFormat integerFormat = new DecimalFormat("#,###");
    private static final DecimalFormat realNumberFormat = new DecimalFormat("#,##0.00");

    public static void printSheet(SheetDTO sheet) {
        System.out.println("Version: " + sheet.getVersion());
        System.out.println("Sheet Name: " + sheet.getName());
        System.out.print("    |");
        for (int col = 0; col < sheet.getSheetSize().getNumCols(); col++) {
            char columnLetter = (char) ('A' + col);
            System.out.printf(" %-" + sheet.getSheetSize().getCellSize().getColWidth() + "s |", columnLetter);
        }
        System.out.println();
        for (int row = 0; row < sheet.getSheetSize().getNumRows(); row++) {
            System.out.printf("%02d  |", row + 1);
            for (int col = 0; col < sheet.getSheetSize().getNumCols(); col++) {
                CellDTO cell = sheet.getCell(row, col);
                String cellValue = cell != null && cell.getContent() != null
                        ? formatCellContent(cell.getContent()) : "";
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

    // Helper method to format the cell content
    private static String formatCellContent(String content) {
        content = content.trim(); // Trim whitespaces at the start and end

        // Check if it's a boolean value
        if ("TRUE".equalsIgnoreCase(content) || "FALSE".equalsIgnoreCase(content)) {
            return content.toUpperCase();
        }

        // Check if it's a numerical value
        try {
            // Try to parse as integer first
            int intValue = Integer.parseInt(content);
            return integerFormat.format(intValue);
        } catch (NumberFormatException ignored) {
            // Not an integer, try as double
        }

        try {
            // Try to parse as a real number (double)
            double doubleValue = Double.parseDouble(content);
            if (doubleValue == Math.floor(doubleValue)) {
                // Check if the double value is actually an integer (e.g., 123.00)
                return integerFormat.format((long) doubleValue);
            }
            return realNumberFormat.format(doubleValue);
        } catch (NumberFormatException ignored) {
            // Not a numerical value, continue as string
        }

        // Return as string (already trimmed)
        return content;
    }
}
