package menu.menuItems.selectionTwo;

import engine.Engine;
import engine.EngineImpl;
import fromEngine.SheetDTO;
import menu.MenuItem;
import menu.MenuItemListener;
import menu.menuItems.selectionOne.LoadFile;

public class SheetPresentation implements MenuItemListener {

    public SheetPresentation(MenuItem menuItem) {
        menuItem.addItemSelectedListener(this);
    }

    @Override
    public void reportItemSelectedFromMenu() {
        try {
            SheetDTO sheet = LoadFile.engine.displaySheet();
            SheetPrinter.printSheet(sheet);
        } catch (Exception e) {
            System.out.println("There is no sheet loaded. Please load a sheet first and then try again.");
        }
    }
}
