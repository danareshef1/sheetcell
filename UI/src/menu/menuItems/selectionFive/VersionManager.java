package menu.menuItems.selectionFive;

import engine.Engine;
import engine.EngineImpl;
import fromEngine.SheetDTO;
import menu.MenuItem;
import menu.MenuItemListener;
import menu.menuItems.selectionTwo.SheetPrinter;

import java.util.Map;
import java.util.Scanner;

public class VersionManager implements MenuItemListener {
    private final Engine engine = EngineImpl.getInstance();

    public VersionManager(MenuItem menuItem) {
        menuItem.addItemSelectedListener(this);
    }

    @Override
    public void reportItemSelectedFromMenu() {
        displayVersions();
    }

    private void displayVersions() {
        try {
            engine.ensureSheetLoaded();
            displayVersionsTable(engine.displaySheet().getVersion());
            int version = getVersion(engine.displaySheet().getVersion());
            System.out.println("Displaying sheet for version " + version + ":");
            SheetPrinter.printSheet(engine.getSheetByVersion(version));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayVersionsTable(int version) {
        try {
            System.out.println("Version number | Number of changes");
            System.out.println("--------------- -------------------");
            for (int i = 0; i < version; i++) {
                int versionNumber = i + 1;
                int changesNumber = engine.getSheetByVersion(versionNumber).getCounterChangedCells();
                System.out.printf("%-15d | %-17d%n", versionNumber, changesNumber);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private int getVersion(int numVersions){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Please Enter the version number of the sheet you would like to view: ");
            try{
                int versionNumber = Integer.parseInt(scanner.nextLine());
                if(versionNumber >= 1 && versionNumber <= numVersions){
                    return versionNumber;
                }else {
                    System.out.printf("Invalid version number. Please enter a number between 1 to %d.%n", numVersions);
                }
            }catch (NumberFormatException e){
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void printSheet(SheetDTO sheetDTO) {
        // Assuming the SheetDTO has a method toString or similar for printing
        System.out.println(sheetDTO);
    }
}