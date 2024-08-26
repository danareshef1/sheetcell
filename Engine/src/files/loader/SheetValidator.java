package files.loader;

import files.jaxb.schema.generated.STLCell;
import files.jaxb.schema.generated.STLSheet;
import parser.StringValidator;
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

        //        התאים המגדירים שימוש בפונקציות מכווינים לתאים המכילים מידע שמתאים לארגומנטים של הפונקציה -לוודא - אקספשן מתאים

        validateCells(stlSheet, numRows, numCols);
    }

    private static void validateCells(STLSheet stlSheet, int numRows, int numCols){
        List<STLCell> cells = stlSheet.getSTLCells().getSTLCell();
        for (STLCell cell : cells) {
            char column = cell.getColumn().charAt(0);
            int row = cell.getRow();

            // Check if cell is within valid range
            if (!isInBounds(column-'A', 0, numCols-1)
            || !isInBounds(row, 0, numRows+1)){
                throw new IllegalArgumentException("cell" + cell.getRow() + ":" + cell.getColumn() + " is not in valid range." +
                        "The range is " + numRows + " rows and " + numCols + " columns.");
            }

//            // Validate function arguments, if applicable
//            if (isFunctionCell(cell)) {
//                validateFunctionCell(cell, stlSheet);
//            }
        }
    }

    private static boolean isFunctionCell(STLCell cell) {
        return StringValidator.isFunction(cell.getSTLOriginalValue());
    }

    private static void validateFunctionCell(STLCell cell, STLSheet stlSheet) {
        // Implement logic to check if the cell references other cells with compatible types
        // For example, a SUM function should reference numerical cells

        List<String> referencedCells = extractReferencedCells(cell.getSTLOriginalValue());

        for (String ref : referencedCells) {
            STLCell referencedCell = findCellByReference(ref, stlSheet);
            if (referencedCell == null) {
                throw new IllegalArgumentException("Function cell " + cell.getRow() + ":" + cell.getColumn() +
                        " references a non-existent cell: " + ref);
            }
            // Add checks to ensure the referenced cell's content is of the expected type
            if (!isValidForFunction(referencedCell, cell)) {
                throw new IllegalArgumentException("Function cell " + cell.getRow() + ":" + cell.getColumn() +
                        " references a cell " + ref + " with an incompatible data type.");
            }
        }
    }

    private static List<String> extractReferencedCells(String content) {
        // Extract references from the function string, assuming a format like "FUNC:SUM(A1,B2)"
        // This is a placeholder implementation; adjust as needed
        return List.of(content.substring(content.indexOf('(') + 1, content.indexOf(')')).split(","));
    }

    private static STLCell findCellByReference(String reference, STLSheet stlSheet) {
        // Find a cell in the sheet by its reference (e.g., "A1")
        for (STLCell cell : stlSheet.getSTLCells().getSTLCell()) {
            if (reference.equals(cell.getColumn() + String.valueOf(cell.getRow()))) {
                return cell;
            }
        }
        return null;
    }

    private static boolean isValidForFunction(STLCell referencedCell, STLCell functionCell) {
        // Implement your logic to validate the referenced cell's content against the function's requirements
        // For example, if the function is SUM, check if the referenced cell contains a numerical value
        String functionName = functionCell.getSTLOriginalValue().substring(5, functionCell.getSTLOriginalValue().indexOf('('));

        // Placeholder logic: check if the function requires numerical values and validate accordingly
        if ("SUM".equalsIgnoreCase(functionName)) {
            return isNumeric(referencedCell.getSTLOriginalValue());
        }
        // Add more cases for other functions as needed
        return true;
    }

    private static boolean isNumeric(String content) {
        // Check if the content is numeric
        try {
            Double.parseDouble(content);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isInBounds(int value, int min, int max) {
        return value >= min && value <= max;
    }
}
