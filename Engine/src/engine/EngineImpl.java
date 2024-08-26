package engine;

import files.loader.SheetFactory;
import fromEngine.CellDTO;
import fromEngine.SheetDTO;
import jakarta.xml.bind.JAXBException;
import sheet.Sheet;
import sheet.cell.Cell;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;
import fromUI.CellUpdateDTO;
import fromUI.DisplayCellDTO;
import fromUI.LoadSheetDTO;
import sheet.version.Version;
import sheet.version.VersionImpl;
//import sheet.version.VersionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineImpl implements Engine {
    private static EngineImpl instance;
    private Sheet sheet;
//    private final Map<Integer, Version> versions = new HashMap<>(); // Store versions and their data
    ///private VersionManager versionManager;

    public static synchronized EngineImpl getInstance() {
        if (instance == null) {
            instance = new EngineImpl();
        }
        return instance;
    }

    @Override
    public void loadSheetFromFile(LoadSheetDTO loadSheetDTO) throws JAXBException, IOException {
        sheet = SheetFactory.loadFile(LoadSheetDTO.getFilePath());
        //versionManager.saveVersion(sheet);
    }

    @Override
    public void updateCellValue(CellUpdateDTO cellUpdateDTO) {
        Coordinate coordinateToUpdate = CoordinateFactory.cellIdToRowCol(cellUpdateDTO.getCellId().toUpperCase());
        this.sheet = sheet.updateCellValueAndCalculate(coordinateToUpdate.getRow(), coordinateToUpdate.getColumn(), cellUpdateDTO.getNewValue());
        //versionManager.saveVersion(sheet);
    }


    @Override
    public CellDTO displayCellValue(DisplayCellDTO displayCellDTO) {
        Coordinate parsedCoordinate = CoordinateFactory.cellIdToRowCol(displayCellDTO.getCellId().toUpperCase());
        CoordinateFactory.coordinateValidator(displayCellDTO.getCellId(), sheet.getSheetSize());
        Cell cell = sheet.getCell(parsedCoordinate.getRow(), parsedCoordinate.getColumn());
        return CellDTO.createCellDTO(cell);
    }

    @Override
    public SheetDTO displaySheet() {
        return SheetDTO.createSheetDTO(sheet);
    }

    @Override
    public void ensureSheetLoaded() {
        if (sheet == null) {
            throw new RuntimeException("There is no sheet loaded. Please load a sheet first and then try again.");
        }
    }

    @Override
    public void displaySheetVersions() {

    }
}
