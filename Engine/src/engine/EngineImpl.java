package engine;

import files.loader.SheetFactory;
import fromEngine.CellDTO;
import fromEngine.SheetDTO;
import jakarta.xml.bind.JAXBException;
import sheet.SheetImpl;
import sheet.coordinate.CoordinateFactory;
import fromUI.CellUpdateDTO;
import fromUI.DisplayCellDTO;
import fromUI.LoadSheetDTO;

import java.io.IOException;

public class EngineImpl implements Engine {
    private SheetImpl sheet;

    @Override
    public void loadSheetFromFile(LoadSheetDTO loadSheetDTO) throws JAXBException, IOException {
        this.sheet = SheetFactory.loadFile(LoadSheetDTO.getFilePath());
    }

    @Override
    public void updateCellValue(CellUpdateDTO cellUpdateDTO) {
        int[] parsedCoordinate = CoordinateFactory.cellIdToRowCol(cellUpdateDTO.getCellId());
        CoordinateFactory.coordinateValidator(cellUpdateDTO.getCellId(), sheet.getSheetSize());
        sheet.setCell(parsedCoordinate[0], parsedCoordinate[1], cellUpdateDTO.getNewValue());
    }

    @Override
    public CellDTO displayCellValue(DisplayCellDTO displayCellDTO) {
        return null;
    }

    @Override
    public SheetDTO displaySheet() {
        return SheetDTO.createSheetDTO(sheet);
    }

    // Map<Integer, DataFromEngineToDisplaySheet> displaySheetMap = new HashMap<>();


}
