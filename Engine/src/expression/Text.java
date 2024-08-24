package expression;

import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;

public class Text implements Expression {

    private final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        return new EffectiveValueImpl(CellType.STRING, text);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.STRING;
    }

    @Override
    public String toString() {
        return text;
    }
}