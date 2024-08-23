package expression;

import sheet.Sheet;

import java.util.List;

@FunctionalInterface
public interface Expression<T> {
    T evaluate();
}