package expression;

import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;

public class Bool implements Expression{
    private final boolean num;

    public Bool(boolean num) {
        this.num = num;
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        return new EffectiveValueImpl(CellType.BOOLEAN, num);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.BOOLEAN;
    }

    @Override
    public String toString() {
        return Boolean.toString(num);
    }
}
