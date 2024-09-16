package expression;

import expression.systemicExpression.REF;
import sheet.SheetReadActions;
import sheet.cell.EffectiveValue;

import java.io.Serializable;
import sheet.cell.CellType;

import static parser.ExpressionFactory.createExpression;

public interface Expression extends Serializable {
    EffectiveValue evaluate(SheetReadActions sheet);

    CellType getFunctionResultType();

    default boolean checkIfRefType(Expression expression, SheetReadActions sheet, CellType cellType) {
        if (expression instanceof REF) {
            Expression expression1 = createExpression(sheet, expression.evaluate(sheet).getValue().toString());
            return !expression1.getFunctionResultType().equals(cellType) && !expression1.getFunctionResultType().equals(CellType.UNKNOWN);
        }
        return false;
    }
}