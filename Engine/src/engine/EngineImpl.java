package engine;

import files.loader.SheetFactory;
import fromEngine.CellDTO;
import fromEngine.SheetDTO;
import jakarta.xml.bind.JAXBException;
import sheet.Sheet;
import sheet.cell.Cell;
import sheet.cell.CellImpl;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;
import fromUI.CellUpdateDTO;
import fromUI.DisplayCellDTO;
import fromUI.LoadSheetDTO;

import java.io.IOException;

public class EngineImpl implements Engine {
    private static EngineImpl instance;
    private Sheet sheet;

    public static synchronized EngineImpl getInstance() {
        if (instance == null) {
            instance = new EngineImpl();
        }
        return instance;
    }

    @Override
    public void loadSheetFromFile(LoadSheetDTO loadSheetDTO) throws JAXBException, IOException {
        sheet = SheetFactory.loadFile(LoadSheetDTO.getFilePath());
    }

    @Override
    public void updateCellValue(CellUpdateDTO cellUpdateDTO) {
        ensureSheetLoaded();
        Coordinate coordinateToUpdate = CoordinateFactory.cellIdToRowCol(cellUpdateDTO.getCellId());
        this.sheet = sheet.updateCellValueAndCalculate(coordinateToUpdate.getRow(), coordinateToUpdate.getColumn(), cellUpdateDTO.getNewValue());
    }


    @Override
    public CellDTO displayCellValue(DisplayCellDTO displayCellDTO) {
        ensureSheetLoaded();
        Coordinate parsedCoordinate = CoordinateFactory.cellIdToRowCol(displayCellDTO.getCellId());
        CoordinateFactory.coordinateValidator(displayCellDTO.getCellId(), sheet.getSheetSize());
        Cell cell = sheet.getCell(parsedCoordinate.getRow(), parsedCoordinate.getColumn());
        return CellDTO.createCellDTO(cell);
    }

    @Override
    public SheetDTO displaySheet() {
        ensureSheetLoaded();
        return SheetDTO.createSheetDTO(sheet);
    }

    private void ensureSheetLoaded() {
        if (sheet == null) {
            throw new RuntimeException("There is no sheet loaded. Please load a sheet first and then try again.");
        }
    }
}
