package menu.menuItems.selectionThree;

import engine.Engine;
import engine.EngineImpl;
import fromEngine.CellDTO;
import fromUI.DisplayCellDTO;
import menu.MenuItem;
import menu.MenuItemListener;

import java.util.Scanner;

public class CellPresentation implements MenuItemListener {
    public final Engine engine = EngineImpl.getInstance();

    public CellPresentation(MenuItem menuItem) {
        menuItem.addItemSelectedListener(this);
    }

    @Override
    public void reportItemSelectedFromMenu() {
        Scanner scanner = new Scanner(System.in);
        try{
            engine.ensureSheetLoaded();
            System.out.println("Please enter the cell identity that you wish to display (e.g., A1): ");
            String cellId = scanner.nextLine().trim();
            CellDTO cellDTO = engine.displayCellValue(new DisplayCellDTO(cellId.toUpperCase()));
            if (cellDTO != null) {
                CellPrinter.displayCellDetails(cellDTO);
            } else {
                System.out.println("Cell " + cellId + " not found.");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}