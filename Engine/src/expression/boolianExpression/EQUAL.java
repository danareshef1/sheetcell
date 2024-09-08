package expression.boolianExpression;

import expression.Expression;
import expression.functionsValidators.FunctionValidator;
import parser.ExpressionParser;
import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;


public class EQUAL extends FunctionValidator implements BooleanExpression, ExpressionParser<Expression> {
    private final Expression expression1;
    private final Expression expression2;

    public EQUAL(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.functionName = "EQUAL";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        EffectiveValue leftValue = expression1.evaluate(sheet);
        EffectiveValue rightValue = expression2.evaluate(sheet);

        if (leftValue.equals(rightValue)) {
            return new EffectiveValueImpl(expression1.getFunctionResultType(), "TRUE");
        } else {
            return new EffectiveValueImpl(CellType.STRING, "FALSE");
        }
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.BOOLEAN;
    }

    @Override
    public Expression parse(Expression... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("EQUAL function requires exactly 2 arguments, but got " + args.length);
        }

        return new EQUAL(args[0], args[1]);
    }

    public String toString(SheetReadActions sheet) {
        return "{" + functionName + "," + expression1.evaluate(sheet) + "," + expression2.evaluate(sheet) + "}";
    }
}
