package expression;

import sheet.Sheet;

import java.io.Serializable;
import java.util.List;

@FunctionalInterface
public interface Expression<T> extends Serializable {
    T evaluate();
}