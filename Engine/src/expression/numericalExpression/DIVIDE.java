package expression.numericalExpression;

import expression.BinaryExpression;
import expression.Expression;
import expression.Number;
import parser.ExpressionParser;

public class DIVIDE extends BinaryExpression<Double> implements NumericalExpression, ExpressionParser<Expression<Double>> {

    public DIVIDE(Expression<Double> expression1, Expression<Double> expression2) {
        super(expression1, expression2);
        this.functionName = "DIVIDE";
    }

    @Override
    protected Double evaluate(Double e1, Double e2) {
        if (e1 == null || e2 == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        return e2 == 0 ? Double.NaN : e1 / e2;
    }

    @Override
    public Expression<Double> parse(Expression<?>... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("DIVIDE function requires exactly 2 arguments, but got " + args.length);
        }

// Check if both arguments are numerical expressions
        if (!NumericalExpression.isNumericalExpression(args[0]) || !NumericalExpression.isNumericalExpression(args[1])) {
            throw new IllegalArgumentException("Invalid argument types for DIVIDE function. Expected numerical expressions, but got "
                    + args[0].getClass().getSimpleName() + " and " + args[1].getClass().getSimpleName());
        }

        return new DIVIDE((Expression<Double>) args[0], (Expression<Double>) args[1]);
    }
}
