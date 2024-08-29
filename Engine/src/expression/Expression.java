package expression;

import sheet.SheetReadActions;
import sheet.cell.EffectiveValue;

import java.io.Serializable;
import sheet.cell.CellType;

public interface Expression extends Serializable {
    EffectiveValue evaluate(SheetReadActions sheet);
    CellType getFunctionResultType();
}