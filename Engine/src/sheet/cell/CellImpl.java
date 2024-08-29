package sheet.cell;

import expression.Expression;
import parser.ExpressionFactory;
import sheet.SheetReadActions;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateImpl;
import java.util.ArrayList;
import java.util.List;

public class CellImpl implements Cell {
    private final Coordinate coordinate;
    private EffectiveValue effectiveValue = null;
    private String originalValue;
    private int version;
    private List<Cell> dependsOnValues;
    private List<Cell> influencingOnValues;
    private String cellId;
    private final SheetReadActions sheet;

    public CellImpl(int row, int column, String originalValue, int version, SheetReadActions sheet) {
        this.sheet = sheet;
        this.coordinate = new CoordinateImpl(row, column);
        this.originalValue = originalValue;
        this.version = version;
        this.dependsOnValues = new ArrayList<>();
        this.influencingOnValues = new ArrayList<>();
        this.cellId = (columnToString(column)) + "" + (row+1);
        if (originalValue != null) {
            calculateEffectiveValue();
        }
    }

    public static char columnToString(int col) {
        return (char)('A' + (col));
    }

    @Override
    public String getCellId() {
        return cellId;
    }

    @Override
    public void removeDependencies() {
        // Remove this cell from the influencing list of all its dependencies
        for (Cell dependedCell : dependsOnValues) {
            dependedCell.getInfluencingOnValues().remove(this);
        }

        // Clear the dependencies list
        dependsOnValues.clear();
    }

    @Override
    public Coordinate getCoordinate(){
        return coordinate;
    }

    @Override
    public String getOriginalValue() {
        return originalValue;
    }

    @Override
    public void setCellOriginalValue(String value,boolean first) {
        // Remove the old dependencies first
        if(!first) {
            removeDependencies();
        }
        // Set the new value
        this.originalValue = value;

        // Recalculate the effective value if the new value is not null
        if (originalValue != null) {
            calculateEffectiveValue();
        } else {
            effectiveValue = null;
        }
    }

    @Override
    public EffectiveValue getEffectiveValue() {
        return this.effectiveValue;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void updateVersion() {
        this.version++;
    }

    @Override
    public List<Cell> getDependsOnValues() {
        return dependsOnValues;
    }

    @Override
    public void addDependsOnValue(Cell cell) {
        if (!dependsOnValues.contains(cell)) {
            dependsOnValues.add(cell);
        }
    }

    @Override
    public List<Cell> getInfluencingOnValues() {
        return influencingOnValues;
    }

    @Override
    public void addInfluencingOnValues(Cell cell) {
        if (!influencingOnValues.contains(cell)) {
            influencingOnValues.add(cell);
        }
    }

    public EffectiveValue getContent() {
        if (effectiveValue != null) {
            return effectiveValue;
        }
        return null;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean calculateEffectiveValue() {
        Expression expression = ExpressionFactory.createExpression(sheet, originalValue, cellId);
        EffectiveValue newEffectiveValue = expression.evaluate(sheet);

        if (newEffectiveValue.equals(effectiveValue)) {
            return false;
        } else {
            this.effectiveValue = newEffectiveValue;
            return true;
        }
    }
}