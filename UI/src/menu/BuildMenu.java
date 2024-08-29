package menu;

import jakarta.xml.bind.JAXBException;
import menu.menuItems.selectionFive.VersionManager;
import menu.menuItems.selectionFour.CellUpdater;
import menu.menuItems.selectionOne.LoadFile;
import menu.menuItems.selectionSeven.LoadSystemState;
import menu.menuItems.selectionSix.SaveSystemState;
import menu.menuItems.selectionThree.CellPresentation;
import menu.menuItems.selectionTwo.SheetPresentation;

import java.io.IOException;

public class BuildMenu {

    public static MainMenu buildMenu() throws IOException, JAXBException {
        MainMenu mainMenu = new MainMenu("Sheet-Cell");
        MenuItem oneSelection = mainMenu.addSubMenuItem("Load an XML file");
        MenuItem twoSelection = mainMenu.addSubMenuItem("Present the Sheet");
        MenuItem threeSelection = mainMenu.addSubMenuItem("Present a cell in the sheet");
        MenuItem fourSelection = mainMenu.addSubMenuItem("Insert/Update value to a cell in the sheet");
        MenuItem fiveSelection = mainMenu.addSubMenuItem("Present versions of the sheet");
        MenuItem sixSelection = mainMenu.addSubMenuItem("Save system state");
        MenuItem sevenSelection = mainMenu.addSubMenuItem("Load system state");

        LoadFile loadFile = new LoadFile(oneSelection);
        SheetPresentation presentSheet = new SheetPresentation(twoSelection);
        CellPresentation presentCell = new CellPresentation(threeSelection);
        CellUpdater sheetUpdater = new CellUpdater(fourSelection);
        VersionManager versionManager = new VersionManager(fiveSelection);
        SaveSystemState saveSystemState = new SaveSystemState(sixSelection);
        LoadSystemState loadSystemState = new LoadSystemState(sevenSelection);

        return mainMenu;
    }
}
