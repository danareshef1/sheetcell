package expression;

import sheet.Sheet;
import sheet.SheetReadActions;

public abstract class TrinaryExpression<T, S> extends FunctionValidator implements Expression<T> {

    private final Expression<T> expression1;
    private final Expression<S> expression2;
    private final Expression<S> expression3;
    protected int argsNumberForFunc = 4;
    private SheetReadActions sheet;

    public TrinaryExpression(Expression<T> expression1, Expression<S> expression2, Expression<S> expression3) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }

    @Override
    public T evaluate() {
        return evaluate(expression1.evaluate(), expression2.evaluate(), expression3.evaluate(), sheet);
    }

    protected abstract T evaluate(T evaluate1, S evaluate2, S evaluate3, SheetReadActions sheet);

    @Override
    public String toString() {
        return "{" + functionName + "," + expression1.evaluate() + "," + expression2.evaluate() + "," + expression3.evaluate() + "}";
    }
}
