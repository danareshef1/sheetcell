package expression.numericalExpression;

import expression.Expression;
import expression.Number;
import expression.systemicExpression.SystemicExpression;
import parser.StringValidator;

public interface NumericalExpression extends Expression<Double> {

    public static boolean isNumericalExpression(Expression<?> expression) {
        // Check if the expression is an instance of a numerical expression
        if (expression instanceof Number) {
            return true; // It's a direct numerical value
        }
        if (expression instanceof SystemicExpression) {
            return true;
        }
        return expression instanceof NumericalExpression; // It's a numerical function
    }
}
