package expression.boolianExpression;

import expression.Expression;
import expression.functionsValidators.FunctionValidator;
import parser.ExpressionParser;
import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;


public class NOT extends FunctionValidator implements BooleanExpression, ExpressionParser<Expression> {
    private final Expression expression;

    public NOT(Expression expression1) {
        this.expression = expression1;
        this.functionName = "NOT";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        EffectiveValue value = expression.evaluate(sheet);
        EffectiveValueImpl effectiveValue = null;

        if (expression.getFunctionResultType() == CellType.BOOLEAN) {
            if (value.getValue().toString().toUpperCase().equals("TRUE"))
                effectiveValue = new EffectiveValueImpl(CellType.BOOLEAN, "FALSE");
            else if (value.getValue().toString().toUpperCase().equals("FALSE"))
                effectiveValue =  new EffectiveValueImpl(CellType.BOOLEAN, "TRUE");
        } else {
            effectiveValue = new EffectiveValueImpl(CellType.BOOLEAN, "UNKNOWN");
        }
        return effectiveValue;
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.BOOLEAN;
    }

    @Override
    public Expression parse(Expression... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("NOT function requires exactly 2 arguments, but got " + args.length);
        }

        return new NOT(args[0]);
    }

    public String toString(SheetReadActions sheet) {
        return "{" + functionName + "," + expression.evaluate(sheet) + "}";
    }
}
