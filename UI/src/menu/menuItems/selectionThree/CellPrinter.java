package menu.menuItems.selectionThree;

import sheet.Sheet;
import sheet.SheetImpl;
import sheet.cell.CellImpl;

import java.util.Scanner;

public class CellPrinter {

    public static void displayCellDetails(Sheet sheet) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter cell identifier (A1, B2..): ");
            String input = scanner.nextLine().trim();
            if (input.length() < 2) {
                System.out.println("Invalid input. Please enter in the format: <Column><Row>.");
                continue;
            }
            char columnLetter = input.charAt(0);
            int rowNumber;
            try {
                rowNumber = Integer.parseInt(input.substring(1));
            } catch (NumberFormatException e) {
                System.out.println("Invalid row number. Please enter a valid number.");
                continue;
            }
            int colIndex = columnLetter - 'A';
            if (rowNumber < 1 || rowNumber > sheet.getSheetSize().getNumRows() || colIndex < 0 || colIndex >= sheet.getSheetSize().getNumCols()) {
                System.out.println("Cell out of bounds.");
                continue;
            }
            CellImpl cell = (CellImpl)sheet.getCell(rowNumber-1, colIndex);
            if (cell == null) {
                System.out.println("This cell is empty.");
                continue;
            }
            System.out.println("Cell ID: " + cell.getCellId());
            System.out.println("Original Value: " + (cell.getOriginalValue() != null ? cell.getOriginalValue(): "None"));
            System.out.println("Effective Value: " + (cell.getEffectiveValue() != null ? cell.getEffectiveValue() : "None"));
            System.out.println("Last Version Changed: " + cell.getVersion());
            System.out.print("Depends On: ");
            if (cell.getDependsOnValues().isEmpty()) {
                System.out.println("None");
            } else {
                for (Object dep : cell.getDependsOnValues()) {
                    System.out.print(dep + " ");
                }
                System.out.println();
            }
            System.out.print("Affects: ");
            if (cell.getInfluencingOnValues().isEmpty()) {
                System.out.println("None");
            } else {
                for (Object aff : cell.getInfluencingOnValues()) {
                    System.out.print(aff + " ");
                }
                System.out.println();
            }
            // Exit the loop after displaying details
            break;
        }
    }
}
