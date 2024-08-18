package menu;

import menu.menuItems.selectionFour.SheetUpdater;
import menu.menuItems.selectionThree.CellPresentation;
import menu.menuItems.selectionThree.CellPrinter;
import menu.menuItems.selectionTwo.SheetPresentation;
import sheet.SheetImpl;

public class BuildMenu {

    public static MainMenu buildMenu() {
        MainMenu mainMenu = new MainMenu("Sheet-Cell");
        MenuItem oneSelection = mainMenu.addSubMenuItem("Load an XML file");
        MenuItem twoSelection = mainMenu.addSubMenuItem("Present the Sheet");
        MenuItem threeSelection = mainMenu.addSubMenuItem("Present a cell in the sheet");
        MenuItem fourSelection = mainMenu.addSubMenuItem("Insert/Update value to a cell in the sheet");
        MenuItem fiveSelection = mainMenu.addSubMenuItem("Present versions of the sheet");

        SheetImpl sheet = SetSheet.setSheet();

        SheetPresentation presentSheet = new SheetPresentation(twoSelection, sheet);
        CellPresentation presentCell = new CellPresentation(threeSelection, sheet);
        SheetUpdater sheetUpdater = new SheetUpdater(fourSelection, sheet);

        return mainMenu;
    }
}
