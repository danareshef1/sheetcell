package engine;

import files.loader.SheetFactory;
import fromEngine.CellDTO;
import fromEngine.SheetDTO;
import jakarta.xml.bind.JAXBException;
import sheet.Sheet;
import sheet.SheetImpl;
import sheet.cell.Cell;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;
import fromUI.CellUpdateDTO;
import fromUI.DisplayCellDTO;
import fromUI.LoadSheetDTO;
import sheet.layout.LayoutImpl;
//import sheet.version.VersionManager;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class EngineImpl implements Engine {
    private static EngineImpl instance;
    private Sheet sheet;
    private final List<Sheet> mainSheet = new ArrayList<>();

    public static synchronized EngineImpl getInstance() {
        if (instance == null) {
            instance = new EngineImpl();
        }
        return instance;
    }

    @Override
    public void loadSheetFromFile(LoadSheetDTO loadSheetDTO) throws JAXBException, IOException {
        sheet = SheetFactory.loadFile(LoadSheetDTO.getFilePath());
        addSheet(sheet);
        //versionManager.saveVersion(sheet);
    }

    @Override
    public void updateCellValue(CellUpdateDTO cellUpdateDTO) {
        Coordinate coordinateToUpdate = CoordinateFactory.cellIdToRowCol(cellUpdateDTO.getCellId().toUpperCase());
        this.sheet = sheet.updateCellValueAndCalculate(coordinateToUpdate.getRow(), coordinateToUpdate.getColumn(), cellUpdateDTO.getNewValue(), false);
        addSheet(sheet);
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
    public List<SheetDTO> displaySheetVersions() {
        List<SheetDTO> sheetDTOs = new ArrayList<>();
        for (Sheet sheet : mainSheet) {
            sheetDTOs.add(SheetDTO.createSheetDTO(sheet));
        }
        return sheetDTOs;
    }

    @Override
    public SheetDTO getSheetByVersion(int version) {
        Optional<Sheet> sheet = mainSheet.stream().filter(s -> s.getVersion() == version).findFirst();
        if (sheet.isEmpty()){
            throw new IllegalArgumentException("There is no sheet with version " + version);
        }
        return SheetDTO.createSheetDTO(sheet.get());
    }

    public void setCellValue(String cellId, String value) throws ParseException {
        Coordinate coordinate = CoordinateFactory.cellIdToRowCol(cellId.toUpperCase());
        if (mainSheet.isEmpty()) {
            throw new IllegalStateException("There is no sheet available to update");
        }
        Sheet currentSheet = mainSheet.getLast();
        Sheet newSheet = createSheetFrom(currentSheet);
        newSheet.incrementVersion();

        newSheet.updateCellValueAndCalculate(coordinate.getRow(), coordinate.getColumn(), value, false);
        newSheet.getCell(coordinate.getRow(), coordinate.getColumn()).setVersion(newSheet.getVersion());
        for (Cell coor : newSheet.getCell(coordinate.getRow(), coordinate.getColumn()).getDependsOnValues()){
            Coordinate newCoor = CoordinateFactory.cellIdToRowCol(coor.getCellId().toUpperCase());
            newSheet.getCell(newCoor.getRow(), newCoor.getColumn()).setVersion(newSheet.getVersion());
        }
        mainSheet.add(newSheet);
    }

    public Sheet createSheetFrom(Sheet sheet){
        String newSheetName = sheet.getName();
        LayoutImpl newSheetLayout = sheet.getSheetSize();
        int cellsWhoChanged = sheet.getCountChangedCells();
        return new SheetImpl(newSheetName, newSheetLayout, cellsWhoChanged);
    }

    public void addSheet(Sheet newSheet) {
        if (newSheet != null){
            mainSheet.add(newSheet);
        }
        else {
            throw new IllegalArgumentException("The new sheet cant be null.");
        }
    }
}
