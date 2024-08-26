package expression.numericalExpression;

import expression.Expression;
import expression.functionsValidators.FunctionValidator;
import parser.ExpressionParser;
import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;


public class ABS extends FunctionValidator implements NumericalExpression, ExpressionParser<Expression> {
    private final Expression expression;

    public ABS(Expression expression) {
        this.expression = expression;
        this.functionName = "ABS";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        EffectiveValue expression1 = expression.evaluate(sheet);

        try {
            double result = Math.abs(expression1.extractValueWithExpectation(Double.class));
            return new EffectiveValueImpl(CellType.NUMERIC, result);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The function ABS expecting for 1 number.");
        }
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }

    @Override
    public Expression parse(Expression... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("ABS function requires exactly 2 arguments, but got " + args.length);
        }

        // Check if both arguments are numerical expressions
        if (!args[0].getFunctionResultType().equals(CellType.NUMERIC) && !args[0].getFunctionResultType().equals(CellType.UNKNOWN)) {
            throw new IllegalArgumentException("Invalid argument types for ABS function. Expected NUMERIC, but got " + args[0].getFunctionResultType());
        }

        return new ABS(args[0]);
    }

    public String toString(SheetReadActions sheet) {
        return "{" + functionName + "," + expression.evaluate(sheet) + "}";
    }
}