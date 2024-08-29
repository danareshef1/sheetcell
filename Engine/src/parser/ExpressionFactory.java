
package parser;

import expression.*;
import expression.Number;
import expression.functionsValidators.FunctionValidator;
import expression.numericalExpression.*;
import expression.stringExpression.CONCAT;
import expression.stringExpression.SUB;
import expression.systemicExpression.REF;
import sheet.SheetReadActions;
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

    public static Expression createExpression(SheetReadActions sheet, String expression) {
        Expression result;

        if (StringValidator.isNumber(expression))
            result = new Number(Double.parseDouble(expression));
        else if (StringValidator.isBool(expression))
            result = new Bool(Boolean.parseBoolean(expression));
        else if (StringValidator.isFunction(expression)) {
            result = parseFunction(expression, sheet);
        } else {
            result = new Text(expression);
        }

        return result;
    }

    public static Expression parseFunction(String expression, SheetReadActions sheet) {
        String functionName = FunctionValidator.getFunctionName(expression);
        ExpressionParser<?> parser = parsers.get(functionName);

        if (parser != null) {
            FunctionValidator validator = new FunctionValidator();
            validator.functionName = functionName;

            String[] args = validator.functionParts(expression);

            Expression[] parsedArgs = new Expression[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                parsedArgs[i - 1] = createExpression(sheet, args[i]);
            }

            return parser.parse(parsedArgs);
        } else {
            throw new IllegalArgumentException("Unknown function: " + functionName);
        }
    }

}
