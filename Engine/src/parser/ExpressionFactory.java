package parser;

import expression.*;
import expression.Number;
import expression.functionsValidators.FunctionValidator;
import expression.numericalExpression.*;
import expression.stringExpression.CONCAT;
import expression.stringExpression.SUB;
import expression.systemicExpression.REF;
import sheet.Sheet;
import sheet.SheetReadActions;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;

import java.util.HashMap;
import java.util.Map;

public class ExpressionFactory {

    private static final Map<String, ExpressionParser<?>> parsers = new HashMap<>();

    static {
        parsers.put("PLUS", new PLUS(null, null));
        parsers.put("MINUS", new MINUS(null, null));
        parsers.put("TIMES", new TIMES(null, null));
        parsers.put("ABS", new ABS(null));
        parsers.put("DIVIDE", new DIVIDE(null, null));
        parsers.put("MOD", new MOD(null, null));
        parsers.put("POW", new POW(null, null));
        parsers.put("CONCAT", new CONCAT(null, null));
        parsers.put("SUB", new SUB(null, null, null));
        parsers.put("REF", new REF(null));
    }

    public static Expression createExpression(SheetReadActions sheet, String expression, String cellId) {
        //expression = expression.trim();
        Expression result;

        if (StringValidator.isNumber(expression))
            result = new Number(Double.parseDouble(expression));
        else if (StringValidator.isBool(expression))
            result = new Bool(Boolean.parseBoolean(expression));
        else if (StringValidator.isFunction(expression)) {
            result = parseFunction(expression, sheet, cellId);
            if (result instanceof REF){
                String refCellId = FunctionValidator.getCellIdForRef(expression);
                Coordinate ref = CoordinateFactory.cellIdToRowCol(refCellId.toUpperCase());
                Coordinate cellIdCoordinate = CoordinateFactory.cellIdToRowCol(cellId.toUpperCase());
                sheet.getCell(cellIdCoordinate.getRow(), cellIdCoordinate.getColumn()).addDependsOnValue(ref);
                sheet.getCell(ref.getRow(), ref.getColumn()).addInfluencingOnValues(cellIdCoordinate);
            }
        }
        else
            result = new Text(expression);

        return result;
    }

    public static Expression parseFunction(String expression, SheetReadActions sheet, String cellId) {
        String functionName = FunctionValidator.getFunctionName(expression);
        ExpressionParser<?> parser = parsers.get(functionName);

        if (parser != null) {
            // Use the relevant validator for this function type
            FunctionValidator validator = new FunctionValidator();
            validator.functionName = functionName;

            // Validate the function parts
            String[] args = validator.functionParts(expression);

            // Parse each argument recursively
            Expression[] parsedArgs = new Expression[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                parsedArgs[i - 1] = createExpression(sheet, args[i], cellId);
            }

            // Pass the parsed arguments to the specific parser
            return parser.parse(parsedArgs);
        } else {
            throw new IllegalArgumentException("Unknown function: " + functionName);
        }
    }

}
