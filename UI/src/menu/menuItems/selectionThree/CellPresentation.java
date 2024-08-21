package menu.menuItems.selectionThree;

import menu.MenuItem;
import menu.MenuItemListener;
import sheet.Sheet;
import sheet.SheetImpl;

public class CellPresentation implements MenuItemListener {

    private Sheet sheet;

        public CellPresentation(MenuItem menuItem, Sheet sheet) {
        menuItem.addItemSelectedListener(this);
        this.sheet = sheet;
    }

    @Override
    public void reportItemSelectedFromMenu(){
        CellPrinter.displayCellDetails(sheet);
    }
}
