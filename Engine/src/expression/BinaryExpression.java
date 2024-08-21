package expression;

import java.util.List;

public abstract class BinaryExpression<T> extends FunctionValidator implements Expression<T> {

    private final Expression<T> expression1;
    private final Expression<T> expression2;
    protected final int argsNumberForFunc = 3;

    public BinaryExpression(Expression<T> expression1, Expression<T> expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public T evaluate() {
        return evaluate(expression1.evaluate(), expression2.evaluate());
    }

    protected abstract T evaluate(T evaluate1, T evaluate2);

    @Override
    public String toString() {
        return "{" + functionName + "," + expression1.evaluate() + "," + expression2.evaluate() + "}";
    }
}
