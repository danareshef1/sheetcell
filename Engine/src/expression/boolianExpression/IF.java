package expression.boolianExpression;

import expression.*;
import expression.functionsValidators.FunctionValidator;
import parser.ExpressionParser;
import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;

public class IF extends FunctionValidator implements BooleanExpression, ExpressionParser<Expression> {
    private final Expression expression1;
    private final Expression expression2;
    private final Expression expression3;

    public IF(Expression expression1, Expression expression2, Expression expression3) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.functionName = "IF";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        EffectiveValue condition = expression1.evaluate(sheet);
        EffectiveValue thenValue = expression2.evaluate(sheet);
        EffectiveValue elseValue = expression3.evaluate(sheet);

        if (expression2.getFunctionResultType() != expression3.getFunctionResultType()) {
            throw new IllegalArgumentException("The 'then' and 'else' expressions must return the same type. Got: " +
                    expression2.getFunctionResultType() + " and " + expression3.getFunctionResultType());
        }

        if (condition.getValue() instanceof Boolean) {
            boolean conditionResult = (Boolean) condition.getValue();
            return conditionResult ? thenValue : elseValue;
        } else {
            throw new IllegalArgumentException("Condition must evaluate to a boolean value.");
        }
    }

    @Override
    public CellType getFunctionResultType() {
        return expression2.getFunctionResultType();
    }

    @Override
    public Expression parse(Expression... args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("IF function requires exactly 3 arguments, but got " + args.length);
        }

//        // Check if both arguments are numerical expressions
//        if ((!args[0].getFunctionResultType().equals(CellType.STRING) && !args[0].getFunctionResultType().equals(CellType.UNKNOWN))
//                || (!args[1].getFunctionResultType().equals(CellType.NUMERIC) && !args[1].getFunctionResultType().equals(CellType.UNKNOWN))
//                || (!args[2].getFunctionResultType().equals(CellType.NUMERIC) && !args[2].getFunctionResultType().equals(CellType.UNKNOWN))) {
//            throw new IllegalArgumentException("Invalid argument types for SUB function. Expected STRING, but got " + args[0].getFunctionResultType()
//                    + " , " + args[1].getFunctionResultType() + " and " + args[2].getFunctionResultType());
//        }

        return new IF(args[0], args[1], args[2]);
    }

    public String toString(SheetReadActions sheet) {
        return "{" + functionName + "," + expression1.evaluate(sheet) + "," + expression2.evaluate(sheet) + "," + expression3.evaluate(sheet) + "}";
    }
}
