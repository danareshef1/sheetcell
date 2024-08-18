package expression;

@FunctionalInterface
public interface Expression<T> {
    T evaluate();
}