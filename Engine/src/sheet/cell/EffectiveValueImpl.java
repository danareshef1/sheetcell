package sheet.cell;

import java.util.Objects;

public class EffectiveValueImpl implements EffectiveValue {
    private final CellType cellType;
    private final Object value;

    public EffectiveValueImpl(CellType cellType, Object value) {
        this.cellType = cellType;
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public <T> T extractValueWithExpectation(Class<T> type) {
        if (cellType.isAssignableFrom(type)) {
            return type.cast(value);
        }
        else {
            throw new IllegalArgumentException("Attempted to extract value of the type " + type + " from " + value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        EffectiveValueImpl that = (EffectiveValueImpl) o;

        return (Objects.equals(this.value, that.value));
    }

    @Override
    public int hashCode() {
        int result = cellType != null ? cellType.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}