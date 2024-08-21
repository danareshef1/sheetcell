package expression;

import sheet.Sheet;

public abstract class UnaryExpression<T> extends FunctionValidator implements Expression<T> {

    private final Expression<T> expression1;
    private final int argsNumberForFunc = 2;

    public UnaryExpression(Expression<T> expression1) {
        this.expression1 = expression1;
    }

    @Override
    public T evaluate() {
        return evaluate(expression1.evaluate());
    }

    protected abstract T evaluate(T evaluate1);

    @Override
    public String toString() {
        return "{" + functionName + "," + expression1.evaluate() + "}";
    }

    public abstract Expression<Object> evaluate(Sheet sheet);
}