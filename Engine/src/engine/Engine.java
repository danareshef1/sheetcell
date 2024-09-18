
package engine;

import fromEngine.CellDTO;
import fromEngine.SheetDTO;
import jakarta.xml.bind.JAXBException;
import fromUI.CellUpdateDTO;
import fromUI.DisplayCellDTO;
import fromUI.LoadSheetDTO;

import java.io.IOException;

public interface Engine {

    void loadSheetFromFile(LoadSheetDTO loadSheetDTO) throws JAXBException, IOException;
    void updateCellValue(CellUpdateDTO cellUpdateDTO);
    CellDTO displayCellValue(DisplayCellDTO displayCellDTO);
    SheetDTO displaySheet();
    void ensureSheetLoaded();
    SheetDTO getSheetByVersion(int version);
    void saveSystemState(LoadSheetDTO data);
    void loadSystemState(LoadSheetDTO data);
}
