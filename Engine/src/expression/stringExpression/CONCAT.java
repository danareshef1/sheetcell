package expression.stringExpression;

import expression.BinaryExpression;
import expression.Expression;
import parser.ExpressionParser;

public class CONCAT extends BinaryExpression<String> implements StringExpression, ExpressionParser<CONCAT> {

    public CONCAT(Expression<String> expression1, Expression<String> expression2) {

        super(expression1, expression2);
        this.functionName = "CONCAT";
    }

    @Override
    protected String evaluate(String e1, String e2) {
        return e1 + e2;
    }

    @Override
    public CONCAT parse(Expression<?>... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("PLUS function requires exactly 2 arguments.");
        }

        return new CONCAT((Expression<String>) args[0], (Expression<String>) args[1]);
    }
}
