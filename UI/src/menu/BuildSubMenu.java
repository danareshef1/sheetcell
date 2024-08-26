package menu;

import menu.menuItems.selectionFour.CellUpdater;
import menu.menuItems.selectionThree.CellPresentation;
import menu.menuItems.selectionTwo.SheetPresentation;
import sheet.SheetImpl;

public class BuildSubMenu implements MenuItemListener{

    private String title = "Sheet-Cell";
    private MenuItem menuItem;

    public BuildSubMenu(MenuItem prevMenu) {
        menuItem = prevMenu.addSubMenuItem(title);
        menuItem.addItemSelectedListener(this);
    }

    @Override
    public void reportItemSelectedFromMenu() {
        buildSubMenu();
    }

    public static void buildSubMenu() {
        MainMenu mainMenu = new MainMenu("Sheet-Cell");
        MenuItem oneSelection = mainMenu.addSubMenuItem("Load an XML file");
        MenuItem twoSelection = mainMenu.addSubMenuItem("Present the Sheet");
        MenuItem threeSelection = mainMenu.addSubMenuItem("Present a cell in the sheet");
        MenuItem fourSelection = mainMenu.addSubMenuItem("Insert/Update value to a cell in the sheet");
        MenuItem fiveSelection = mainMenu.addSubMenuItem("Present versions of the sheet");

        SheetPresentation presentSheet = new SheetPresentation(twoSelection);
        CellPresentation presentCell = new CellPresentation(threeSelection);
        CellUpdater sheetUpdater = new CellUpdater(fourSelection);
    }
}
