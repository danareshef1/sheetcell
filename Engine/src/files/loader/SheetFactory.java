package files.loader;

import engine.EngineImpl;
import files.jaxb.schema.generated.STLCell;
import files.jaxb.schema.generated.STLCells;
import files.jaxb.schema.generated.STLSheet;
import jakarta.xml.bind.JAXBException;
import sheet.cell.Cell;
import sheet.cell.CellImpl;
import sheet.cellSize.CellSizeImpl;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;
import sheet.layout.LayoutImpl;
import sheet.Sheet;
import sheet.SheetImpl;

import java.io.*;

public class SheetFactory {
    private static Sheet currentSheet;
    private static LayoutImpl layout;
    private static CellSizeImpl cellSize;

    public static Sheet loadFile(String filePath) throws IOException, JAXBException {
        STLSheet stlSheet = XmlFileLoader.validateLoadXmlFile(filePath);
        SheetValidator.validateSheet(stlSheet);
        buildSheetFromSTLSheet(stlSheet);
        return currentSheet;
    }

    public static void buildSheetFromSTLSheet(STLSheet stlSheet) {
        cellSize = new CellSizeImpl(stlSheet.getSTLLayout().getSTLSize().getColumnWidthUnits(),
                stlSheet.getSTLLayout().getSTLSize().getRowsHeightUnits());
        layout = new LayoutImpl(stlSheet.getSTLLayout().getRows(), stlSheet.getSTLLayout().getColumns(), cellSize);
        currentSheet = new SheetImpl(stlSheet.getName(), layout, stlSheet.getSTLCells().getSTLCell().size());

        for (int i = 0; i < stlSheet.getSTLCells().getSTLCell().size(); i++) {
            int[] cellId = cellIdToRowCol(stlSheet.getSTLCells().getSTLCell().get(i).getRow(),
                    stlSheet.getSTLCells().getSTLCell().get(i).getColumn().toUpperCase());
            //currentSheet.setCell(cellId[0], cellId[1],
            //    stlSheet.getSTLCells().getSTLCell().get(i).getSTLOriginalValue());
            String originalValue = stlSheet.getSTLCells().getSTLCell().get(i).getSTLOriginalValue();
            Cell newCell = new CellImpl(cellId[0], cellId[1], 0, currentSheet);
            currentSheet.addCell(newCell);
            newCell.setCellOriginalValue(originalValue, true);
            currentSheet = currentSheet.updateCellValueAndCalculate(cellId[0], cellId[1], originalValue, true);
        }
    }


    public static int[] cellIdToRowCol(int row, String column) {
        // Convert column letter(s) to zero-based index
        int colIndex = 0;
        for (int i = 0; i < column.length(); i++) {
            colIndex *= 26;
            colIndex += (column.charAt(i) - 'A' + 1);
        }
        colIndex--; // Adjust for zero-based index

        int rowIndex = row - 1; // Convert 1-based row to zero-based index

        return new int[]{rowIndex, colIndex};
    }

}
