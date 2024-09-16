
package expression.numericalExpression;

import expression.Expression;
import expression.functionsValidators.FunctionValidator;
import parser.ExpressionParser;
import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;


public class PERCENT extends FunctionValidator implements NumericalExpression, ExpressionParser<Expression> {
    private final Expression expression1;
    private final Expression expression2;

    public PERCENT(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.functionName = "PERCENT";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        EffectiveValue part = expression1.evaluate(sheet);
        EffectiveValue whole = expression2.evaluate(sheet);

        // Check if both arguments are numerical expressions
        if ((expression1.getFunctionResultType().equals(CellType.NUMERIC) && !expression1.getFunctionResultType().equals(CellType.UNKNOWN))
                || (!expression2.getFunctionResultType().equals(CellType.NUMERIC) && !expression2.getFunctionResultType().equals(CellType.UNKNOWN))) {
            return new EffectiveValueImpl(CellType.NUMERIC, "NaN");
        }

        if (checkIfRefType(expression1, sheet, CellType.NUMERIC) || checkIfRefType(expression2, sheet, CellType.NUMERIC)){
            return new EffectiveValueImpl(CellType.NUMERIC, "!UNDEFINED!");
        }

        try {
            double result = (part.extractValueWithExpectation(Double.class) * whole.extractValueWithExpectation(Double.class)) / 100;
            return new EffectiveValueImpl(CellType.NUMERIC, result);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The function PERCENT expecting for 2 numbers.");
        }
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }

    @Override
    public Expression parse(Expression... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("PERCENT function requires exactly 2 arguments, but got " + args.length);
        }

        return new PERCENT(args[0], args[1]);
    }

    public String toString(SheetReadActions sheet) {
        return "{" + functionName + "," + expression1.evaluate(sheet) + "," + expression2.evaluate(sheet) + "}";
    }
}