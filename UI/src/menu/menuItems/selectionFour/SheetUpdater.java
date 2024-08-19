package menu.menuItems.selectionFour;

import menu.MenuItem;
import menu.MenuItemListener;
import menu.menuItems.selectionTwo.SheetPrinter;
import sheet.SheetImpl;
import sheet.cell.Cell;

import java.util.Scanner;

public class SheetUpdater implements MenuItemListener {

    private SheetImpl sheet;

    public SheetUpdater(MenuItem menuItem, SheetImpl sheet) {
        menuItem.addItemSelectedListener(this);
        this.sheet = sheet;
    }

    public void updateCell() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the cell address (e.g., 4A): ");
        String cellAddress = scanner.nextLine().trim();

        int row = parseRow(cellAddress);
        int column = parseColumn(cellAddress);

        if (row < 0 || column < 0 || row >= sheet.getSheetSize().getNumRows() || column >= sheet.getSheetSize().getNumCols()) {
            System.out.println("Invalid cell address.");
            return;
        }

        Cell cell = sheet.getCell(row, column);

        System.out.println("Cell address: " + cellAddress);
        System.out.println("Original value: " + cell.getOriginalValue());
        System.out.println("Effective value: " + cell.getEffectiveValue());

        System.out.print("Enter new value for the cell: ");
        String newValue = scanner.nextLine().trim();

        if (newValue.isEmpty()) {
            newValue = null; // Handle clearing the cell
        }

        try {
            sheet.setCell(row, column, newValue);
            cell.calculateEffectiveValue();

            // Update dependencies
            updateDependencies(cell);

            sheet.incrementVersion();
            sheet.incrementCellChanged();
            cell.updateVersion();
            System.out.println("Spreadsheet version updated to " + sheet.getVersion());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        SheetPrinter.printSheet(sheet);
    }

    private int parseRow(String cellAddress) {
        String rowPart = cellAddress.replaceAll("\\D", ""); // Extract digits
        return Integer.parseInt(rowPart) - 1; // Convert 1-based to 0-based
    }

    private int parseColumn(String cellAddress) {
        String colPart = cellAddress.replaceAll("\\d", ""); // Extract letters
        return colPart.charAt(0) - 'A'; // Convert column letter to index
    }

    private void updateDependencies(Cell cell) {
        // Implement logic to recalculate dependencies
        // This will involve recalculating all cells that depend on `cell`
        for (Cell dependentCell : cell.getDependsOnValues()) {
            dependentCell.calculateEffectiveValue();
            dependentCell.updateVersion();
        }
    }

    @Override
    public void reportItemSelectedFromMenu() {
        updateCell();
    }
}
