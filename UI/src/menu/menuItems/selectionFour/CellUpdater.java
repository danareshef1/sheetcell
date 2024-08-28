package menu.menuItems.selectionFour;

import engine.Engine;
import engine.EngineImpl;
import fromEngine.CellDTO;
import fromEngine.SheetDTO;
import fromUI.CellUpdateDTO;
import fromUI.DisplayCellDTO;
import menu.MenuItem;
import menu.MenuItemListener;
import menu.menuItems.selectionTwo.SheetPrinter;

import java.util.Scanner;

public class CellUpdater implements MenuItemListener {
    private final Engine engine = EngineImpl.getInstance();

    public CellUpdater(MenuItem menuItem) {
        menuItem.addItemSelectedListener(this);
    }

    @Override
    public void reportItemSelectedFromMenu() {
        String cellId = getCellId();
        if (cellId != null) {
            String newValue = getNewValue();
            if (newValue != null) {
                updateCellValue(newValue, cellId.toUpperCase());
            }
        }
    }

    private String getNewValue()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new value for the cell (leave empty to clear): ");
        return scanner.nextLine();
    }

    private void updateCellValue(String newValue, String cellId) {
        try {
            engine.updateCellValue(new CellUpdateDTO(cellId.toUpperCase(), newValue));
            System.out.print("Cell value was updated successfully.");
            System.out.println();
            System.out.println("Here is the updated sheet:");
            SheetDTO sheet = engine.displaySheet();
            SheetPrinter.printSheet(sheet);

        } catch (Exception e) {
            System.out.println("Error updating cell value. " + e.getMessage());
        }
    }

    private String getCellId(){
        Scanner scanner = new Scanner(System.in);

        try {
            engine.ensureSheetLoaded();
            System.out.print("Enter the cell address (e.g., A1): ");
            String cellId = scanner.nextLine().trim();
            CellDTO cellDTO = engine.displayCellValue(new DisplayCellDTO(cellId.toUpperCase()));
            if (cellDTO == null) {
                System.out.println("Cell " + cellId.toUpperCase() + " was not found.");
            }else {
                displayCellValues(cellDTO);
                return cellId;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void displayCellValues(CellDTO cellDTO) {
        System.out.println("Cell details:");
        System.out.println("------------------");
        System.out.println("Cell address: " + cellDTO.getCellId());
        System.out.println("Original value: " + cellDTO.getOriginalValue());
        System.out.println("Effective value: " + cellDTO.getContent());
        System.out.println();
    }
}