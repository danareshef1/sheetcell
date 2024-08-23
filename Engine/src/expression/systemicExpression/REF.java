//////package expression.systemicExpression;
//////
//////import expression.Expression;
//////import expression.UnaryExpression;
//////import expression.stringExpression.StringExpression;
//////import expression.systemicExpression.SystemicExpression;
//////import parser.ExpressionParser;
//////import sheet.cell.Cell;
//////import sheet.Sheet;
//////
//////
//////public class REF extends UnaryExpression<String> implements SystemicExpression, ExpressionParser<Expression<String>> {
//////    private final Sheet sheet;
//////
//////    public REF(Sheet sheet, Expression<String> expression) {
//////        super(expression);
//////        this.sheet = sheet;
//////        this.functionName = "REF";
//////    }
//////
//////    @Override
//////    protected String evaluate(String cellId) {
//////        // Parse the cell ID to get the row and column indices
//////        int[] rowCol = cellIdToRowCol(cellId);
//////        int row = rowCol[0];
//////        int col = rowCol[1];
//////
//////        // Retrieve the referenced cell from the sheet
//////        Cell referencedCell = sheet.getCell(row, col);
//////
//////        if (referencedCell == null) {
//////            throw new IllegalArgumentException("Referenced cell " + cellId + " does not exist.");
//////        }
//////
//////        // Check if the referenced cell is within the sheet size limits
//////        if (row >= sheet.getSheetSize().getNumRows() || col >= sheet.getSheetSize().getNumCols()) {
//////            throw new IllegalArgumentException("Referenced cell " + cellId + " is out of the sheet's bounds.");
//////        }
//////
//////        // Return the effective value of the referenced cell
//////        return referencedCell.getEffectiveValue().evaluate().toString();
//////    }
//////
//////    @Override
//////    public REF parse(Expression<?>... args) {
//////        if (args.length != 1) {
//////            throw new IllegalArgumentException("REF function requires exactly 1 argument, but got " + args.length);
//////        }
//////
//////        // Check if the argument is a valid cell ID (string expression)
//////        if (!SystemicExpression.isSystemicExpression(args[0])) {
//////            throw new IllegalArgumentException("Invalid argument type for REF function. Expected String (cell ID), but got " +
//////                    args[0].getClass().getSimpleName());
//////        }
//////
//////        return new REF(sheet, (Expression<String>) args[0]);
//////    }
//////
//////    private int[] cellIdToRowCol(String cellId) {
//////        if (cellId == null || cellId.length() < 2) {
//////            throw new IllegalArgumentException("Invalid cell ID: " + cellId);
//////        }
//////
//////        // Extract column part and row part
//////        String colPart = cellId.replaceAll("\\d", ""); // Extracts letters
//////        String rowPart = cellId.replaceAll("\\D", ""); // Extracts digits
//////
//////        // Convert column part to zero-based index
//////        int colIndex = colPart.chars().map(c -> c - 'A').sum();
//////        int rowIndex = Integer.parseInt(rowPart) - 1; // Convert 1-based to 0-based
//////
//////        return new int[]{rowIndex, colIndex};
//////    }
//////}
//////
////
////package expression.systemicExpression;
////
////import expression.Expression;
////import expression.UnaryExpression;
////import parser.ExpressionParser;
////import sheet.Sheet;
////import sheet.cell.Cell;
////
////public class REF extends UnaryExpression<String> implements SystemicExpression, ExpressionParser<Expression<String>> {
////    private Sheet sheet;
////    private Cell referencedCell;  // Store a reference to the referenced cell
////
////    public REF(Expression<String> expression) {
////        super(expression);
////        this.functionName = "REF";
////        this.referencedCell = null;
////    }
////
////    @Override
////    protected String evaluate(String cellId) {
////        // If the referenced cell is not set yet, find it based on the cell ID
////        if (referencedCell == null) {
////            int[] rowCol = cellIdToRowCol(cellId);
////            int row = rowCol[0];
////            int col = rowCol[1];
////
////            // Retrieve the referenced cell from the sheet
////            referencedCell = sheet.getCell(row, col);
////        }
////
////        // Return the effective value of the referenced cell
////        return referencedCell.getEffectiveValue().evaluate().toString();
////    }
////
////    @Override
////    public REF parse(Expression<?>... args) {
////        if (args.length != 1) {
////            throw new IllegalArgumentException("REF function requires exactly 1 argument, but got " + args.length);
////        }
////
////        // Check if the argument is a valid cell ID (string expression)
////        if (!SystemicExpression.isSystemicExpression(args[0])) {
////            throw new IllegalArgumentException("Invalid argument type for REF function. Expected String (cell ID), but got " +
////                    args[0].getClass().getSimpleName());
////        }
////
////        return new REF((Expression<String>) args[0]);
////    }
////
////    private int[] cellIdToRowCol(String cellId) {
////        if (cellId == null || cellId.length() < 2) {
////            throw new IllegalArgumentException("Invalid cell ID: " + cellId);
////        }
////
////        // Extract column part and row part
////        String colPart = cellId.replaceAll("\\d", ""); // Extracts letters
////        String rowPart = cellId.replaceAll("\\D", ""); // Extracts digits
////
////        // Convert column part to zero-based index
////        int colIndex = colPart.chars().map(c -> c - 'A').sum();
////        int rowIndex = Integer.parseInt(rowPart) - 1; // Convert 1-based to 0-based
////
////        return new int[]{rowIndex, colIndex};
////    }
////}
//
//package expression.systemicExpression;
//
//import expression.Expression;
//import expression.UnaryExpression;
//import parser.ExpressionParser;
//import sheet.Sheet;
//import sheet.SheetImpl;
//import sheet.cell.Cell;
//import expression.Text;
//import sheet.cell.CellImpl;
//
//public class REF extends UnaryExpression<String> implements SystemicExpression, ExpressionParser<Expression<String>> {
//    private final Cell referencedCell;
//
//
//    public REF(Cell referencedCell) {
//        super(null);  // No need for an inner expression because the cell is directly referenced
//        this.functionName = "REF";
//        this.referencedCell = referencedCell;
//    }
//
//    @Override
//    protected String evaluate(String ignored) {
//        // Return the effective value of the referenced cell
//        return referencedCell.getEffectiveValue().evaluate().toString();
//    }
//
//    @Override
//    public Expression<String> parse(Expression<?>... args) {
//        if (args.length != 1) {
//            throw new IllegalArgumentException("REF function requires exactly 1 argument, but got " + args.length);
//        }
//
//        Expression<?> argument = args[0];
//
//        if (argument instanceof REF) {
//            // If the argument is already a REF, return it directly.
//            return (REF) argument;
//        } else if (argument instanceof Text) {
//            // If the argument is a Text, evaluate it to get the cell ID
//            String cellId = argument.evaluate().toString();
//
//            // Retrieve the referenced cell based on the cell ID
//            Cell referencedCell = getCellById(cellId);
//
//            // Return a new REF instance pointing to this cell
//            return new REF(referencedCell);
//        } else {
//            throw new IllegalArgumentException("Invalid argument type for REF function. Expected REF or Text, but got " +
//                    argument.getClass().getSimpleName());
//        }
//    }
//
//    // Method to retrieve the cell by its ID
//    private Cell getCellById(String cellId) {
//        // This method should be implemented to convert a cell ID (e.g., "B2") into a Cell object.
//        // For example, if you have a map or grid of cells, you could look it up based on the ID.
//        // Here's a dummy example, replace this with actual logic:
//
//        int[] rowCol = cellIdToRowCol(cellId);
//        int row = rowCol[0];
//        int col = rowCol[1];
//
//        // Assume you have some way to retrieve the cell:
//        // return yourSheet.getCell(row, col);
////        return /* logic to get Cell object */;
//        return null;
//    }
//
//    private int[] cellIdToRowCol(String cellId) {
//        if (cellId == null || cellId.length() < 2) {
//            throw new IllegalArgumentException("Invalid cell ID: " + cellId);
//        }
//
//        // Extract column part and row part
//        String colPart = cellId.replaceAll("\\d", ""); // Extracts letters
//        String rowPart = cellId.replaceAll("\\D", ""); // Extracts digits
//
//        // Convert column part to zero-based index
//        int colIndex = colPart.chars().map(c -> c - 'A').sum();
//        int rowIndex = Integer.parseInt(rowPart) - 1; // Convert 1-based to 0-based
//
//        return new int[]{rowIndex, colIndex};
//    }
//}
//
//
//package expression.systemicExpression;
//
//import expression.Expression;
//import expression.UnaryExpression;
//import expression.numericalExpression.NumericalExpression;
//import parser.ExpressionParser;
//import sheet.Sheet;
//import sheet.SheetImpl;
//import sheet.cell.Cell;
//
//public class REF extends UnaryExpression<String> implements SystemicExpression, ExpressionParser<Expression<String>> {
//    private final Cell referencedCell;
//    private Sheet sheet = new SheetImpl(null, null);
//
//    public REF(Cell referencedCell) {
//        super(null);
//        this.functionName = "REF";
//        this.referencedCell = referencedCell;
//        this.sheet = sheet.getInstance();
//    }
//
//    @Override
//    protected String evaluate(String ignored) {
//        // Return the effective value of the referenced cell
//        return referencedCell.getEffectiveValue().evaluate().toString();
//    }
//
//    @Override
//    public Expression<String> parse(Expression<?>... args) {
//        if (args.length != 1) {
//            throw new IllegalArgumentException("REF function requires exactly 1 argument, but got " + args.length);
//        }
//
//        // Assuming args[0] is an instance of Cell, cast it
//        if (!SystemicExpression.isSystemicExpression(args[0])) {
//            throw new IllegalArgumentException("Invalid argument type for REF function. Expected Cell, but got " +
//                    args[0].getClass().getSimpleName());
//        }
//
//        return (Expression<String>) args[0];
//    }
//}
package expression.systemicExpression;

import expression.Expression;
import expression.UnaryExpression;
import parser.ExpressionParser;
import sheet.Sheet;
import sheet.SheetReadActions;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;

public class REF extends UnaryExpression<Object> implements SystemicExpression, ExpressionParser<Expression<Object>> {

    private final Coordinate coordinate;

    public REF(Coordinate coordinate) {
        super(null);
        this.coordinate = coordinate;
    }

    @Override
    public Object evaluate(Object expression, SheetReadActions sheet) {
        String celId = CoordinateFactory.createCoordinate(coordinate.getRow(), coordinate.getColumn()).toString();
        CoordinateFactory.coordinateValidator(celId, sheet.getSheetSize());
        if(sheet.getCell(coordinate.getRow(), coordinate.getColumn()).getOriginalValue() == null)
        {
            throw new IllegalArgumentException("Cell " + celId + " is empty. Cant be referenced to an empty cell.");
        }
        return sheet.getCell(coordinate.getRow(), coordinate.getColumn()).getEffectiveValue();
    }

//    @Override
//    public Expression<Object> evaluate(Sheet sheet) {
//        // error handling if the cell is empty or not found
//        return (Expression<Object>) sheet.getCell(coordinate.getRow(), coordinate.getColumn()).getEffectiveValue();
//    }

//    @Override
//    public Expression<String> parse(Expression<?>... args) {
//        if (args.length != 1) {
//            throw new IllegalArgumentException("REF function requires exactly 1 argument, but got " + args.length);
//        }
//
//        // Check if the argument is a valid cell ID (string expression)
//        if (!SystemicExpression.isSystemicExpression(args[0])) {
//            throw new IllegalArgumentException("Invalid argument type for REF function. Expected String (cell ID), but got " +
//                    args[0].getClass().getSimpleName());
//        }
//
//        return (Expression<String>) args[0];
//    }

    @Override
    public Expression<Object> parse(Expression<?>... args) {
        // validations of the function. it should have exactly one argument
        if (args.length != 1) {
            throw new IllegalArgumentException("REF function requires exactly 1 argument, but got " + args.length);
        }

        // verify indeed argument represents a reference to a cell and create a Coordinate instance. if not ok returns a null. need to verify it
        Coordinate target = CoordinateFactory.cellIdToRowCol(args[0].toString().trim());
        if (target == null) {
            throw new IllegalArgumentException("Invalid argument for REF function. Expected a valid cell reference, but got " + args[0]);
        }

        return new REF(target);
    }
}


