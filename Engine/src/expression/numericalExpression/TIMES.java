package expression.numericalExpression;

import expression.BinaryExpression;
import expression.Expression;
import expression.Number;
import parser.ExpressionParser;

public class TIMES extends BinaryExpression<Double> implements NumericalExpression, ExpressionParser<Expression<Double>> {

    public TIMES(Expression<Double> expression1, Expression<Double> expression2) {

        super(expression1, expression2);
        this.functionName = "TIMES";
    }

    @Override
    protected Double evaluate(Double e1, Double e2) {
        return e1 * e2;
    }

    @Override
    public Expression<Double> parse(Expression<?>... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("PLUS function requires exactly 2 arguments.");
        }

        return new TIMES((Expression<Double>) args[0], (Expression<Double>) args[1]);
    }
}