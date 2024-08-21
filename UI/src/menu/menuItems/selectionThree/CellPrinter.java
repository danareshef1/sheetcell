package menu.menuItems.selectionThree;

import fromEngine.CellDTO;
import fromEngine.SheetDTO;

import java.util.Scanner;

public class CellPrinter {

    public static void displayCellDetails(SheetDTO sheet) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = getUserInput(scanner);

            // Validate the input format
            if (!isValidCellIdentifier(input)) {
                System.out.println("Invalid input. Please enter in the format: <Column><Row> (e.g., A1, B2).");
                continue;
            }

            // Parse the column letter and row number
            char columnLetter = input.charAt(0);
            int rowNumber = parseRowNumber(input.substring(1));
            if (rowNumber == -1) {
                System.out.println("Invalid row number. Please enter a valid number.");
                continue;
            }

            // Convert column letter to index and validate the cell's bounds
            int colIndex = columnLetter - 'A';
            if (!isCellInBounds(sheet, rowNumber, colIndex)) {
                System.out.println("Cell out of bounds.");
                continue;
            }

            // Retrieve and display cell details
            CellDTO cell = sheet.getCell(rowNumber - 1, colIndex);
            if (cell == null) {
                System.out.println("This cell is empty.");
            } else {
                displayCellInfo(cell);
            }
            // Exit the loop after displaying details
            break;
        }
    }

    private static String getUserInput(Scanner scanner) {
        System.out.print("Enter cell identifier (A1, B2..): ");
        return scanner.nextLine().trim();
    }

    private static boolean isValidCellIdentifier(String input) {
        return input.length() >= 2 && Character.isLetter(input.charAt(0)) && input.substring(1).matches("\\d+");
    }

    private static int parseRowNumber(String rowPart) {
        try {
            return Integer.parseInt(rowPart);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean isCellInBounds(SheetDTO sheet, int rowNumber, int colIndex) {
        return rowNumber >= 1 && rowNumber <= sheet.getSheetSize().getNumRows() &&
                colIndex >= 0 && colIndex < sheet.getSheetSize().getNumCols();
    }

    private static void displayCellInfo(CellDTO cell) {
        System.out.println("Cell ID: " + cell.getCellId());
        System.out.println("Original Value: " + (cell.getOriginalValue() != null ? cell.getOriginalValue() : "None"));
        System.out.println("Effective Value: " + (cell.getContent() != null ? cell.getContent() : "None"));
        System.out.println("Last Version Changed: " + cell.getVersion());
        displayDependencies("Depends On: ", cell.getDependsOnValues());
        displayDependencies("Affects: ", cell.getInfluencingOnValues());
    }

    private static void displayDependencies(String label, Iterable<?> dependencies) {
        System.out.print(label);
        if (!dependencies.iterator().hasNext()) {
            System.out.println("None");
        } else {
            for (Object dep : dependencies) {
                System.out.print(dep + " ");
            }
            System.out.println();
        }
    }
}
