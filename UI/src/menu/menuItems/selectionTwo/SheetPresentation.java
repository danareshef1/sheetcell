package menu.menuItems.selectionTwo;

import menu.MenuItem;
import menu.MenuItemListener;
import sheet.Sheet;
import sheet.SheetImpl;

public class SheetPresentation implements MenuItemListener {

    private Sheet sheet;

    public SheetPresentation(MenuItem menuItem, Sheet sheet) {
        menuItem.addItemSelectedListener(this);
        this.sheet = sheet;
    }

    @Override
    public void reportItemSelectedFromMenu(){
        SheetPrinter.printSheet(sheet);
    }
}
