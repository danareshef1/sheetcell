package expression;

import sheet.Sheet;
import sheet.SheetReadActions;

import java.util.List;

public abstract class UnaryExpression<T> extends FunctionValidator implements Expression<T> {

    private final Expression<T> expression1;
    private final int argsNumberForFunc = 2;
    private SheetReadActions sheet;

    public UnaryExpression(Expression<T> expression1) {
        this.expression1 = expression1;
    }

    @Override
    public T evaluate() {
        return evaluate(expression1.evaluate(), sheet);
    }

    protected abstract T evaluate(T evaluate1, SheetReadActions sheet);

    @Override
    public String toString() {
        return "{" + functionName + "," + expression1.evaluate() + "}";
    }
}