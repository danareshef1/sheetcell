package expression.numericalExpression;

import expression.BinaryExpression;
import expression.Expression;
import expression.Number;
import parser.ExpressionParser;

public class MOD extends BinaryExpression<Double> implements NumericalExpression, ExpressionParser<Expression<Double>> {

    public MOD(Expression<Double> expression1, Expression<Double> expression2) {

        super(expression1, expression2);
        this.functionName = "MOD";
    }

    @Override
    protected Double evaluate(Double e1, Double e2) {
        return e1 % e2;
    }

    @Override
    public Expression<Double> parse(Expression<?>... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("PLUS function requires exactly 2 arguments.");
        }

        return new MOD((Expression<Double>) args[0], (Expression<Double>) args[1]);
    }
}
