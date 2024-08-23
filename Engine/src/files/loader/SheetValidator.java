package files.loader;

import files.jaxb.schema.generated.STLCell;
import files.jaxb.schema.generated.STLSheet;
import sheet.cellSize.CellSizeImpl;
import sheet.layout.LayoutImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class SheetValidator {

    public static File validateXmlFile(String filePath) {
        filePath = validateFilePath(filePath);

        // Check if file exists
        validateFileExists(filePath);

        // Check if file has .xml extension
        if (!filePath.endsWith(".xml")) {
            throw new IllegalArgumentException("The file is not an XML file: " + filePath);
        }
        return new File(filePath);
    }

    public static String validateFilePath(String filePath) {
        Path path = Paths.get(filePath);
        Optional.ofNullable(path.getParent())
                .flatMap(parent -> Stream.iterate(parent, Objects::nonNull, Path::getParent)
                        .filter(p -> !Files.exists(p))
                        .findFirst())
                .ifPresent(nonExistentDir -> {
                    throw new InvalidPathException(nonExistentDir.toString(), "Parent directory does not exist.");
                });
        return filePath;
    }

    public static void validateFileExists(String filePath) {
        Optional.of(Files.exists(Paths.get(filePath)))
                .filter(Boolean::booleanValue)
                .orElseThrow(() -> new IllegalArgumentException("File does not exist."));
    }

    public static void validateSheet(STLSheet stlSheet) {
        // Validate the file according to the specified requirements
        int numRows = stlSheet.getSTLLayout().getRows();
        int numCols = stlSheet.getSTLLayout().getColumns();
        CellSizeImpl cellSize = new CellSizeImpl(stlSheet.getSTLLayout().getSTLSize().getColumnWidthUnits(), stlSheet.getSTLLayout().getSTLSize().getRowsHeightUnits());
        LayoutImpl layout = new LayoutImpl(numRows, numCols, cellSize);
        if (!isInBounds(numRows, layout.getMinimumRows(), layout.getMaximumRows())) {
            throw new IllegalArgumentException("Rows number is out of valid range.");
        }
        if (!isInBounds(numCols, layout.getMinimumCols(), layout.getMaximumCols())){
            throw new IllegalArgumentException("Columns number is out of valid range.");
        }

        validateCells(stlSheet, numRows, numCols);

//        התאים המגדירים שימוש בפונקציות מכווינים לתאים המכילים מידע שמתאים לארגומנטים של הפונקציה
    }

    private static void validateCells(STLSheet stlSheet, int numRows, int numCols){
        List<STLCell> cells = stlSheet.getSTLCells().getSTLCell();
        for (STLCell cell : cells) {
            char column = cell.getColumn().charAt(0);
            if (!isInBounds(column-'A', 0, numCols-1)
            || !isInBounds(cell.getRow(), 0, numRows+1)){
                throw new IllegalArgumentException("cell" + cell.getRow() + ":" + cell.getColumn() + " is not in valid range." +
                        "The range is " + numRows + " rows and " + numCols + " columns.");
            }
        }
    }

    private static boolean isInBounds(int value, int min, int max) {
        return value >= min && value <= max;
    }
}
