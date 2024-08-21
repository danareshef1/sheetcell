package expression;

import java.util.List;

@FunctionalInterface
public interface Expression<T> {
    //T evaluate(List<T> args);
    T evaluate();
}