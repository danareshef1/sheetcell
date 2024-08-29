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

import java.io.*;
import java.util.*;

public class EngineImpl implements Engine {
    private static Engine instance;
    private Sheet sheet;
    private final List<Sheet> mainSheet = new ArrayList<>();

    public static synchronized Engine getInstance() {
        if (instance == null) {
            instance = new EngineImpl();
        }
        return instance;
    }

    @Override
    public void loadSheetFromFile(LoadSheetDTO loadSheetDTO) throws JAXBException, IOException {
        sheet = SheetFactory.loadFile(LoadSheetDTO.getFilePath());
        addSheet(sheet);
    }

    @Override
    public void updateCellValue(CellUpdateDTO cellUpdateDTO) {
        Coordinate coordinateToUpdate = CoordinateFactory.cellIdToRowCol(cellUpdateDTO.getCellId().toUpperCase());
        this.sheet = sheet.updateCellValueAndCalculate(coordinateToUpdate.getRow(), coordinateToUpdate.getColumn(), cellUpdateDTO.getNewValue(), false);
        addSheet(sheet);
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
    public SheetDTO getSheetByVersion(int version) {
        Optional<Sheet> sheet = mainSheet.stream().filter(s -> s.getVersion() == version).findFirst();
        if (sheet.isEmpty()){
            throw new IllegalArgumentException("There is no sheet with version " + version);
        }
        return SheetDTO.createSheetDTO(sheet.get());
    }

    public void addSheet(Sheet newSheet) {
        if (newSheet != null){
            mainSheet.add(newSheet);
        }
        else {
            throw new IllegalArgumentException("The new sheet cant be null.");
        }
    }

    @Override
    public void saveSystemState(LoadSheetDTO data) {
        String filePath = data.getFilePath();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath + ".ser"))) {
            oos.writeObject(sheet);
        } catch(IOException e){
            throw new RuntimeException("Failed to save system state to " + filePath, e);
        }
    }

    @Override
    public void loadSystemState(LoadSheetDTO data) {
        String filePath = data.getFilePath();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath + ".ser"))) {
            this.sheet = (SheetImpl) ois.readObject();
        }catch(IOException | ClassNotFoundException e){
            throw new RuntimeException("Failed to load system state from " + filePath, e);
        }
    }
}
