package expression.stringExpression;

import expression.*;
import expression.Number;
import expression.numericalExpression.NumericalExpression;
import expression.numericalExpression.PLUS;
import parser.ExpressionParser;
import sheet.Sheet;
import sheet.SheetReadActions;

public class SUB extends TrinaryExpression<String, Double> implements StringExpression, ExpressionParser<Expression<String>> {

    public SUB(Expression<String> expression1, Expression<Double> expression2, Expression<Double> expression3) {

        super(expression1, expression2, expression3);
        this.functionName = "SUB";
    }

    @Override
    protected String evaluate(String e1, Double e2, Double e3, SheetReadActions sheet) {
        if (e1 == null) {
            throw new IllegalArgumentException("Source string cannot be null");
        }

        int start = e2.intValue();
        int end = e3.intValue();

        // Check if the indices are within the valid range
        if (start < 0 || end > e1.length() || start > end) {
            return "!UNDEFINED!";
        }
        return e1.substring(start, end);
    }


    @Override
    public Expression<String> parse(Expression<?>... args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("SUB function requires exactly 3 arguments, but got " + args.length);
        }

        // Check if both arguments are numerical expressions
        if (!StringExpression.isStringExpression(args[0]) || !NumericalExpression.isNumericalExpression(args[1])
                || !NumericalExpression.isNumericalExpression(args[2])) {
            throw new IllegalArgumentException("Invalid argument types for CONCAT function. " +
                    "Expected 1 string expression and 2 more numerical expressions, but got "
                    + args[0].getClass().getSimpleName() + " , " + args[1].getClass().getSimpleName() + " and " + args[2].getClass().getSimpleName());
        }

        Expression<String> textExpr = (Expression<String>) args[0];
        Expression<Double> startExpr = (Expression<Double>) args[1];
        Expression<Double> endExpr = (Expression<Double>) args[2];

        return new SUB(textExpr, startExpr, endExpr);
    }
}

