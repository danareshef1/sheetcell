package menu.menuItems.selectionTwo;

import engine.Engine;
import engine.EngineImpl;
import fromEngine.SheetDTO;
import menu.MenuItem;
import menu.MenuItemListener;
import menu.menuItems.selectionOne.LoadFile;

public class SheetPresentation implements MenuItemListener {
    public final Engine engine = EngineImpl.getInstance();

    public SheetPresentation(MenuItem menuItem) {
        menuItem.addItemSelectedListener(this);
    }

    @Override
    public void reportItemSelectedFromMenu() {
        try {
            engine.ensureSheetLoaded();
            SheetDTO sheet = engine.displaySheet();
            SheetPrinter.printSheet(sheet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}