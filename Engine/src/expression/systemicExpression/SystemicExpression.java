package expression.systemicExpression;

import expression.Expression;
import expression.Number;
import expression.Text;
import sheet.cell.Cell;

public interface SystemicExpression extends Expression<Object> {

    public static boolean isSystemicExpression(Expression<?> expression) {
        // Check if the expression is an instance of a numerical expression
        if (expression instanceof Text) {
            return true; // It's a direct numerical value
        }
        return expression instanceof expression.systemicExpression.SystemicExpression; // It's a systemic function
    }
}

