package menu;

import fromEngine.SheetDTO;
import jakarta.xml.bind.JAXBException;
//import menu.menuItems.selectionFive.VersionManager;
import menu.menuItems.selectionFour.CellUpdater;
import menu.menuItems.selectionOne.LoadFile;
import menu.menuItems.selectionThree.CellPresentation;
import menu.menuItems.selectionTwo.SheetPresentation;

import java.io.IOException;

public class BuildMenu {

    public static SheetDTO sheetDTO;

    public static MainMenu buildMenu() throws IOException, JAXBException {
        MainMenu mainMenu = new MainMenu("Sheet-Cell");
        MenuItem oneSelection = mainMenu.addSubMenuItem("Load an XML file");
        MenuItem twoSelection = mainMenu.addSubMenuItem("Present the Sheet");
        MenuItem threeSelection = mainMenu.addSubMenuItem("Present a cell in the sheet");
        MenuItem fourSelection = mainMenu.addSubMenuItem("Insert/Update value to a cell in the sheet");
        MenuItem fiveSelection = mainMenu.addSubMenuItem("Present versions of the sheet");

        //     /Users/danareshef/IdeaProjects/testXmlForSeetcell1/basic2.xml
        //     /Users/danareshef/IdeaProjects/testXmlForSeetcell1/insurance.xml

        LoadFile loadFile = new LoadFile(oneSelection);
        SheetPresentation presentSheet = new SheetPresentation(twoSelection);
        CellPresentation presentCell = new CellPresentation(threeSelection);
        CellUpdater sheetUpdater = new CellUpdater(fourSelection);
//        VersionManager versionManager = new VersionManager(fiveSelection);

        return mainMenu;
    }
}
