package parser;

import expression.Expression;

public interface ExpressionParser<T extends Expression<?>> {
    T parse(Expression<?>... args);
}
