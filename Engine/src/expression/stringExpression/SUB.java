package expression.stringExpression;

import expression.*;
import expression.Number;
import expression.functionsValidators.FunctionValidator;
import expression.numericalExpression.NumericalExpression;
import expression.numericalExpression.PLUS;
import parser.ExpressionParser;
import sheet.Sheet;
import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;

public class SUB extends FunctionValidator implements StringExpression, ExpressionParser<Expression> {
    private final Expression expression1;
    private final Expression expression2;
    private final Expression expression3;

    public SUB(Expression expression1, Expression expression2, Expression expression3) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.functionName = "SUB";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        EffectiveValue leftValue = expression1.evaluate(sheet);
        EffectiveValue startValue = expression2.evaluate(sheet);
        EffectiveValue endValue = expression3.evaluate(sheet);

        String sourceString = leftValue.extractValueWithExpectation(String.class);
        int start = startValue.extractValueWithExpectation(Integer.class);
        int end = endValue.extractValueWithExpectation(Integer.class);

        if (sourceString == null) {
            throw new IllegalArgumentException("Source string cannot be null");
        }
        if (start < 0 || end > sourceString.length() || start > end) {
            return new EffectiveValueImpl(CellType.STRING, "!UNDEFINED!");
        }

        try {
            String result = sourceString.substring(start, end);
            return new EffectiveValueImpl(CellType.STRING, result);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The function SUB expecting for 1 string and 2 numbers in this order.");
        }
    }


    @Override
    public CellType getFunctionResultType() {
        return CellType.STRING;
    }

    @Override
    public Expression parse(Expression... args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("SUB function requires exactly 3 arguments, but got " + args.length);
        }

        // Check if both arguments are numerical expressions
        if ((!args[0].getFunctionResultType().equals(CellType.STRING) && !args[0].getFunctionResultType().equals(CellType.UNKNOWN))
                || (!args[1].getFunctionResultType().equals(CellType.NUMERIC) && !args[1].getFunctionResultType().equals(CellType.UNKNOWN))
                || (!args[2].getFunctionResultType().equals(CellType.NUMERIC) && !args[2].getFunctionResultType().equals(CellType.UNKNOWN))) {
            throw new IllegalArgumentException("Invalid argument types for SUB function. Expected STRING, but got " + args[0].getFunctionResultType()
                    + " , " + args[1].getFunctionResultType() + " and " + args[2].getFunctionResultType());
        }

        return new SUB(args[0], args[1], args[2]);
    }

    public String toString(SheetReadActions sheet) {
        return "{" + functionName + "," + expression1.evaluate(sheet) + "," + expression2.evaluate(sheet) + "," + expression3.evaluate(sheet) + "}";
    }
}