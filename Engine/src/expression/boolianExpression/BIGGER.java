package expression.boolianExpression;

import expression.Expression;
import expression.functionsValidators.FunctionValidator;
import parser.ExpressionParser;
import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;


public class BIGGER extends FunctionValidator implements BooleanExpression, ExpressionParser<Expression> {
    private final Expression expression1;
    private final Expression expression2;

    public BIGGER(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.functionName = "BIGGER";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        EffectiveValue leftValue = expression1.evaluate(sheet);
        EffectiveValue rightValue = expression2.evaluate(sheet);

        try {
            if (leftValue.extractValueWithExpectation(Double.class) >= rightValue.extractValueWithExpectation(Double.class))
                return new EffectiveValueImpl(CellType.NUMERIC, "TRUE");
            else
                return new EffectiveValueImpl(CellType.NUMERIC, "FALSE");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The function BIGGER expecting for 2 numbers.");
        }
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.BOOLEAN;
    }

    @Override
    public Expression parse(Expression... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("BIGGER function requires exactly 2 arguments, but got " + args.length);
        }

        return new BIGGER(args[0], args[1]);
    }

    public String toString(SheetReadActions sheet) {
        return "{" + functionName + "," + expression1.evaluate(sheet) + "," + expression2.evaluate(sheet) + "}";
    }
}
