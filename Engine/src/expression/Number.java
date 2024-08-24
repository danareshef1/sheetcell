package expression;

import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;

public class Number implements Expression {

    private final double num;

    public Number(double num) {
        this.num = num;
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        return new EffectiveValueImpl(CellType.NUMERIC, num);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }

    @Override
    public String toString() {
        return Double.toString(num);
    }
}