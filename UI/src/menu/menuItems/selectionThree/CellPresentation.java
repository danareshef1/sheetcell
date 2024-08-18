package menu.menuItems.selectionThree;

import menu.MenuItem;
import menu.MenuItemListener;
import sheet.SheetImpl;

public class CellPresentation implements MenuItemListener {

    private SheetImpl sheet;

    public CellPresentation(MenuItem menuItem, SheetImpl sheet) {
        menuItem.addItemSelectedListener(this);
        this.sheet = sheet;
    }

    @Override
    public void reportItemSelectedFromMenu(){
        CellPrinter.displayCellDetails(sheet);
    }
}
