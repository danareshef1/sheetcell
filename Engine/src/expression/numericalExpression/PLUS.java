package expression.numericalExpression;

import expression.*;
import expression.Number;
import expression.stringExpression.StringExpression;
import parser.ExpressionFactory;
import parser.ExpressionParser;
import parser.StringValidator;

import java.util.List;

public class PLUS extends BinaryExpression<Double> implements NumericalExpression, ExpressionParser<Expression<Double>> {

    public PLUS(Expression<Double> expression1, Expression<Double> expression2) {
        super(expression1, expression2);
        this.functionName = "PLUS";
    }


    @Override
    protected Double evaluate(Double e1, Double e2) {
        return e1 + e2;
    }

//    @Override
//    public Expression<Double> parse(String expression) {
//        String[] parts = functionParts(expression);
//
//        double operand1 = Double.parseDouble(parts[1].trim());
//        double operand2 = Double.parseDouble(parts[2].trim());
//
//        return new PLUS(new Number(operand1), new Number(operand2));
//    }

    public Expression<Double> parse(Expression<?>... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("PLUS function requires exactly 2 arguments, but got " + args.length);
        }

        // Check if both arguments are numerical expressions
        if (!NumericalExpression.isNumericalExpression(args[0]) || !NumericalExpression.isNumericalExpression(args[1])) {
            throw new IllegalArgumentException("Invalid argument types for PLUS function. Expected numerical expressions, but got "
                    + args[0].getClass().getSimpleName() + " and " + args[1].getClass().getSimpleName());
        }

        return new PLUS((Expression<Double>) args[0], (Expression<Double>) args[1]);
    }
}
