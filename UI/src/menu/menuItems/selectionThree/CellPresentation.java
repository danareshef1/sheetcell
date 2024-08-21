package menu.menuItems.selectionThree;

import fromUI.DisplayCellDTO;
import menu.MenuItem;
import menu.MenuItemListener;
import menu.menuItems.selectionOne.LoadFile;
import sheet.Sheet;

public class CellPresentation implements MenuItemListener {

    public CellPresentation(MenuItem menuItem) {
        menuItem.addItemSelectedListener(this);
    }

    @Override
    public void reportItemSelectedFromMenu() {
        try{
            CellPrinter.displayCellDetails(LoadFile.engine.displaySheet());
        } catch (Exception e){
        System.out.println("There is no sheet loaded. Please load a sheet first and then try again.");
    }
    }
}
