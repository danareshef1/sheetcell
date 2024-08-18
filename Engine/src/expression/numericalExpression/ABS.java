package expression.numericalExpression;

import expression.Expression;
import expression.Number;
import expression.UnaryExpression;
import parser.ExpressionParser;

public class ABS extends UnaryExpression<Double> implements NumericalExpression, ExpressionParser<Expression<Double>> {

    public ABS(Expression<Double> expression1) {
        super(expression1);
        this.functionName = "ABS";
    }

    @Override
    protected Double evaluate(Double e1) {
        return Math.abs(e1);
    }

    @Override
    public Expression<Double> parse(Expression<?>... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("PLUS function requires exactly 2 arguments.");
        }

        return new ABS((Expression<Double>) args[0]);
    }

}