package expression.numericalExpression;

import expression.BinaryExpression;
import expression.Expression;
import expression.Number;
import parser.ExpressionFactory;
import parser.ExpressionParser;

public class MINUS extends BinaryExpression<Double> implements NumericalExpression, ExpressionParser<Expression<Double>> {

    public MINUS(Expression<Double> expression1, Expression<Double> expression2) {
        super(expression1, expression2);
        this.functionName = "MINUS";
    }

    @Override
    protected Double evaluate(Double e1, Double e2) {
        return e1 - e2;
    }

//    @Override
//    public Expression<Double> parse(String expression) {
//        String[] parts = functionParts(expression);
//
//        double operand1 = Double.parseDouble(parts[1].trim());
//        double operand2 = Double.parseDouble(parts[2].trim());
//
//        return new MINUS(new Number(operand1), new Number(operand2));
//    }

    @Override
    public Expression<Double> parse(Expression<?>... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("PLUS function requires exactly 2 arguments.");
        }

        return new MINUS((Expression<Double>) args[0], (Expression<Double>) args[1]);
    }
}