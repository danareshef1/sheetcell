package sheet.cell;

import java.io.Serializable;

public interface EffectiveValue extends Serializable {
    Object getValue();
    <T> T extractValueWithExpectation(Class<T> type);
}
