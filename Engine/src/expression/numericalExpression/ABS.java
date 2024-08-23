package expression.numericalExpression;

import expression.Expression;
import expression.Number;
import expression.UnaryExpression;
import parser.ExpressionParser;
import sheet.Sheet;
import sheet.SheetReadActions;

import java.util.List;

public class ABS extends UnaryExpression<Double> implements NumericalExpression, ExpressionParser<Expression<Double>> {

    public ABS(Expression<Double> expression1) {
        super(expression1);
        this.functionName = "ABS";
    }

    @Override
    protected Double evaluate(Double e1, SheetReadActions sheet) {
        return Math.abs(e1);
    }

    @Override
    public Expression<Double> parse(Expression<?>... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("ABS function requires exactly 1 argument, but got " + args.length);
        }

        // Check if both arguments are numerical expressions
        if (!NumericalExpression.isNumericalExpression(args[0])) {
            throw new IllegalArgumentException("Invalid argument types for ABS function. Expected numerical expressions, but got "
                    + args[0].getClass().getSimpleName());
        }

        return new ABS((Expression<Double>) args[0]);
    }

}