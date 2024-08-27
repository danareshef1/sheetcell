package expression.systemicExpression;

import expression.Expression;
import expression.functionsValidators.FunctionValidator;
import parser.ExpressionParser;
import sheet.coordinate.CoordinateFactory;
import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.coordinate.Coordinate;
import static sheet.cell.CellImpl.columnToString;

public class REF extends FunctionValidator implements SystemicExpression, ExpressionParser<Expression> {
    private final Coordinate coordinate;

    public REF(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.functionName = "REF";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        // error handling if the cell is empty or not found
        if(sheet.getCell(coordinate.getRow(), coordinate.getColumn()) == null) {
            throw new IllegalArgumentException("Cell " + columnToString(coordinate.getColumn() - 1) + (coordinate.getRow() - 1) + " is out " +
                    "of the sheet boundaries");
        }

        if(sheet.getCell(coordinate.getRow(), coordinate.getColumn()).getOriginalValue() == null) {
            throw new IllegalArgumentException("The cell you chose to ref to is empty. " +
                    "You cant reference to an empty cell");
        }

        return sheet.getCell(coordinate.getRow(), coordinate.getColumn()).getEffectiveValue();
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.UNKNOWN;
    }

    @Override
    public Expression parse(Expression... args) {
        // validations of the function. it should have exactly one argument
        if (args.length != 1) {
            throw new IllegalArgumentException("Invalid number of arguments for REF function. Expected 1, but got " + args.length);
        }

        // verify indeed argument represents a reference to a cell and create a Coordinate instance. if not ok returns a null. need to verify it
        Coordinate target = CoordinateFactory.cellIdToRowCol(args[0].toString().toUpperCase());
        if (target == null) {
            throw new IllegalArgumentException("Invalid argument for REF function. Expected a valid cell reference, but got " + args[0]);
        }

        return new REF(target);
    }
}