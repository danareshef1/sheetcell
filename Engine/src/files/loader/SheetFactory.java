package files.loader;

import files.jaxb.schema.generated.STLSheet;
import jakarta.xml.bind.JAXBException;
import sheet.CellSize;
import sheet.Layout;
import sheet.SheetImpl;

import java.io.*;

public class SheetFactory {
    private static SheetImpl currentSheet;
    private static Layout layout;
    private static CellSize cellSize;

    public static SheetImpl loadFile(String filePath) throws IOException, JAXBException {
        STLSheet stlSheet = XmlFileLoader.validateLoadXmlFile(filePath);
        SheetValidator.validateSheet(stlSheet);
        buildSheetFromSTLSheet(stlSheet);
        return currentSheet;
    }

    public static void buildSheetFromSTLSheet(STLSheet stlSheet) {
        cellSize = new CellSize(stlSheet.getSTLLayout().getSTLSize().getColumnWidthUnits(),
                stlSheet.getSTLLayout().getSTLSize().getRowsHeightUnits());
        layout = new Layout(stlSheet.getSTLLayout().getRows(), stlSheet.getSTLLayout().getColumns(), cellSize);
        currentSheet = new SheetImpl(stlSheet.getName(), layout);

        for (int i=0; i<stlSheet.getSTLCells().getSTLCell().size(); i++){
            int[] cellId = cellIdToRowCol(stlSheet.getSTLCells().getSTLCell().get(i).getRow(),
                    stlSheet.getSTLCells().getSTLCell().get(i).getColumn());
            currentSheet.setCell(cellId[0], cellId[1],
                    stlSheet.getSTLCells().getSTLCell().get(i).getSTLOriginalValue());
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
