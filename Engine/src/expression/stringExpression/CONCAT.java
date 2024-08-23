package expression.stringExpression;

import expression.BinaryExpression;
import expression.Expression;
import expression.numericalExpression.NumericalExpression;
import parser.ExpressionParser;
import sheet.Sheet;
import sheet.SheetReadActions;

import java.util.List;

public class CONCAT extends BinaryExpression<String> implements StringExpression, ExpressionParser<Expression<String>> {

    public CONCAT(Expression<String> expression1, Expression<String> expression2) {

        super(expression1, expression2);
        this.functionName = "CONCAT";
    }

    @Override
    protected String evaluate(String e1, String e2, SheetReadActions sheet) {
        return e1 + e2;
    }

    @Override
    public CONCAT parse(Expression<?>... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("CONCAT function requires exactly 2 arguments, but got " + args.length);
        }

// Check if both arguments are numerical expressions
        if (!StringExpression.isStringExpression(args[0]) || !StringExpression.isStringExpression(args[1])) {
            throw new IllegalArgumentException("Invalid argument types for CONCAT function. Expected string expressions, but got "
                    + args[0].getClass().getSimpleName() + " and " + args[1].getClass().getSimpleName());
        }

        return new CONCAT((Expression<String>) args[0], (Expression<String>) args[1]);
    }
}
