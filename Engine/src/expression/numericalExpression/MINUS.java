package expression.numericalExpression;

import expression.BinaryExpression;
import expression.Expression;
import expression.Number;
import parser.ExpressionFactory;
import parser.ExpressionParser;
import sheet.Sheet;
import sheet.SheetReadActions;

import java.util.List;

public class MINUS extends BinaryExpression<Double> implements NumericalExpression, ExpressionParser<Expression<Double>> {

    public MINUS(Expression<Double> expression1, Expression<Double> expression2) {
        super(expression1, expression2);
        this.functionName = "MINUS";
    }

    @Override
    protected Double evaluate(Double e1, Double e2, SheetReadActions sheet) {
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
            throw new IllegalArgumentException("MINUS function requires exactly 2 arguments, but got " + args.length);
        }

// Check if both arguments are numerical expressions
        if (!NumericalExpression.isNumericalExpression(args[0]) || !NumericalExpression.isNumericalExpression(args[1])) {
            throw new IllegalArgumentException("Invalid argument types for MINUS function. Expected numerical expressions, but got "
                    + args[0].getClass().getSimpleName() + " and " + args[1].getClass().getSimpleName());
        }

        return new MINUS((Expression<Double>) args[0], (Expression<Double>) args[1]);
    }
}