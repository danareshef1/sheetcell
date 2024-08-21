package menu;

import files.loadFileDTO.XmlFactoryImpl;
import jakarta.xml.bind.JAXBException;
import menu.menuItems.selectionFive.VersionManager;
import menu.menuItems.selectionFour.SheetUpdater;
import menu.menuItems.selectionOne.LoadFile;
import menu.menuItems.selectionThree.CellPresentation;
import menu.menuItems.selectionTwo.SheetPresentation;
import sheet.Sheet;
import sheet.SheetImpl;

import java.io.IOException;

public class BuildMenu {

    public static MainMenu buildMenu() throws IOException, JAXBException {
        MainMenu mainMenu = new MainMenu("Sheet-Cell");
        MenuItem oneSelection = mainMenu.addSubMenuItem("Load an XML file");
        MenuItem twoSelection = mainMenu.addSubMenuItem("Present the Sheet");
        MenuItem threeSelection = mainMenu.addSubMenuItem("Present a cell in the sheet");
        MenuItem fourSelection = mainMenu.addSubMenuItem("Insert/Update value to a cell in the sheet");
        MenuItem fiveSelection = mainMenu.addSubMenuItem("Present versions of the sheet");

        SheetImpl sheet = XmlFactoryImpl.loadFile("/Users/danareshef/IdeaProjects/testXmlForSeetcell1/basic.xml");

        LoadFile loadFile = new LoadFile(oneSelection);
        SheetPresentation presentSheet = new SheetPresentation(twoSelection, sheet);
        CellPresentation presentCell = new CellPresentation(threeSelection, sheet);
        SheetUpdater sheetUpdater = new SheetUpdater(fourSelection, sheet);
        VersionManager versionManager = new VersionManager(fiveSelection, sheet);

        return mainMenu;
    }
}
