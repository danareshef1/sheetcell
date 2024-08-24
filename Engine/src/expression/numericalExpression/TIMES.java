package expression.numericalExpression;

import expression.Expression;
import expression.functionsValidators.FunctionValidator;
import parser.ExpressionParser;
import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;


public class TIMES extends FunctionValidator implements NumericalExpression, ExpressionParser<Expression> {
    private final Expression expression1;
    private final Expression expression2;

    public TIMES(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.functionName = "TIMES";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        EffectiveValue leftValue = expression1.evaluate(sheet);
        EffectiveValue rightValue = expression2.evaluate(sheet);

        double result = leftValue.extractValueWithExpectation(Double.class) * rightValue.extractValueWithExpectation(Double.class);

        return new EffectiveValueImpl(CellType.NUMERIC, result);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }

    @Override
    public Expression parse(Expression... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("TIMES function requires exactly 2 arguments, but got " + args.length);
        }

        // Check if both arguments are numerical expressions
        if ((!args[0].getFunctionResultType().equals(CellType.NUMERIC) || !args[1].getFunctionResultType().equals(CellType.NUMERIC))
                && (!args[0].getFunctionResultType().equals(CellType.UNKNOWN) || !args[1].getFunctionResultType().equals(CellType.UNKNOWN))) {
            throw new IllegalArgumentException("Invalid argument types for TIMES function. Expected NUMERIC, but got " + args[0].getFunctionResultType()
                    + " and " + args[1].getFunctionResultType());
        }

        return new TIMES(args[0], args[1]);
    }

    public String toString(SheetReadActions sheet) {
        return "{" + functionName + "," + expression1.evaluate(sheet) + "," + expression2.evaluate(sheet) + "}";
    }
}