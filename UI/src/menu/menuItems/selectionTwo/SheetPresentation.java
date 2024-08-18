package menu.menuItems.selectionTwo;

import menu.MenuItem;
import menu.MenuItemListener;
import sheet.SheetImpl;

public class SheetPresentation implements MenuItemListener {

    private SheetImpl sheet;

    public SheetPresentation(MenuItem menuItem, SheetImpl sheet) {
        menuItem.addItemSelectedListener(this);
        this.sheet = sheet;
    }

    @Override
    public void reportItemSelectedFromMenu(){
        SheetPrinter.printSheet(sheet);
    }
}
