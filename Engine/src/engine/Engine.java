package engine;

import fromEngine.CellDTO;
import fromEngine.SheetDTO;
import jakarta.xml.bind.JAXBException;
import fromUI.CellUpdateDTO;
import fromUI.DisplayCellDTO;
import fromUI.LoadSheetDTO;

import java.io.IOException;
import java.util.List;

public interface Engine {

    void loadSheetFromFile(LoadSheetDTO loadSheetDTO) throws JAXBException, IOException;
    void updateCellValue(CellUpdateDTO cellUpdateDTO);
    CellDTO displayCellValue(DisplayCellDTO displayCellDTO);
    SheetDTO displaySheet();
    void ensureSheetLoaded();
    void displaySheetVersions();
}
