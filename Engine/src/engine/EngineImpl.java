package engine;

import files.loader.SheetFactory;
import fromEngine.CellDTO;
import fromEngine.SheetDTO;
import jakarta.xml.bind.JAXBException;
import sheet.SheetImpl;
import sheet.cell.Cell;
import sheet.cell.CellImpl;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;
import fromUI.CellUpdateDTO;
import fromUI.DisplayCellDTO;
import fromUI.LoadSheetDTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class EngineImpl implements Engine {
    private static EngineImpl instance;
    private SheetImpl sheet;

    public static synchronized EngineImpl getInstance() {
        if (instance == null) {
            instance = new EngineImpl();
        }
        return instance;
    }

    @Override
    public void loadSheetFromFile(LoadSheetDTO loadSheetDTO) throws JAXBException, IOException {
        this.sheet = SheetFactory.loadFile(LoadSheetDTO.getFilePath());
    }

//    @Override
//    public void updateCellValue(CellUpdateDTO cellUpdateDTO) {
////        Coordinate parsedCoordinate = CoordinateFactory.cellIdToRowCol(cellUpdateDTO.getCellId());
////        CoordinateFactory.coordinateValidator(cellUpdateDTO.getCellId(), sheet.getSheetSize());
////        Cell cell = sheet.getCell(parsedCoordinate.getRow(), parsedCoordinate.getColumn());
////        CellDTO updatedCellDTO = CellDTO.createCellDTO(cell);
////        updateCellAndDependencies(updatedCellDTO);
//        ensureSheetLoaded();
//        try {
//            Coordinate parsedCoordinate = CoordinateFactory.cellIdToRowCol(cellUpdateDTO.getCellId());
//            CoordinateFactory.coordinateValidator(cellUpdateDTO.getCellId(), sheet.getSheetSize());
//
//            Cell cell = sheet.getCell(parsedCoordinate.getRow(), parsedCoordinate.getColumn());
//            CellDTO cellDTO = CellDTO.createCellDTO(cell);
//            if (cellDTO == null) {
//                cellDTO = new CellDTO(new CellImpl(parsedCoordinate.getRow(), parsedCoordinate.getColumn(), cellUpdateDTO.getNewValue()));
//                sheet.getActiveCells().put(parsedCoordinate, (Cell)cellDTO);
//            }
//
//            cellDTO.updateCell(cellUpdateDTO.getNewValue());
//            sheet.setCell(parsedCoordinate.getRow(), parsedCoordinate.getColumn(), cellUpdateDTO.getNewValue());
//            sheet.incrementVersion();
//
//            updateCellAndDependencies(cellDTO);
//        } catch (Exception e) {
//            throw new RuntimeException("Error updating cell " + cellUpdateDTO.getCellId());
//        }
//    }

    @Override
    public void updateCellValue(CellUpdateDTO cellUpdateDTO) {
        ensureSheetLoaded();
        try {
            Coordinate parsedCoordinate = CoordinateFactory.cellIdToRowCol(cellUpdateDTO.getCellId());
            CoordinateFactory.coordinateValidator(cellUpdateDTO.getCellId(), sheet.getSheetSize());

            Cell cell = sheet.getCell(parsedCoordinate.getRow(), parsedCoordinate.getColumn());
            if (cell == null) {
                // Create new cell if it doesn't exist
                cell = new CellImpl(parsedCoordinate.getRow(), parsedCoordinate.getColumn(), cellUpdateDTO.getNewValue());
                sheet.setCell(parsedCoordinate.getRow(), parsedCoordinate.getColumn(), cellUpdateDTO.getNewValue());
            }

            CellDTO cellDTO = CellDTO.createCellDTO(cell);
            cellDTO.updateCell(cellUpdateDTO.getNewValue());

            updateCellAndDependencies(cellDTO);
            sheet.incrementVersion();
        } catch (Exception e) {
            throw new RuntimeException("Error updating cell " + cellUpdateDTO.getCellId() + ": " + e.getMessage());
        }
    }

    private void updateCellAndDependencies(CellDTO cellDTO) {
        cellDTO.calculateEffectiveValue();
        for (CellDTO dependentCell : cellDTO.getDependsOnValues()) {
            dependentCell.calculateEffectiveValue();
            dependentCell.updateVersion();
        }
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
