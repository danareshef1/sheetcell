package sheet.cell;

import expression.Expression;
import expression.Text;
import parser.ExpressionFactory;
import sheet.Sheet;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateImpl;

import java.util.ArrayList;
import java.util.List;

public class CellImpl implements Cell {
    private final Coordinate coordinate;
    private Expression<?> effectiveValue = null;
    private String originalValue;
    private int version;
    private List<Cell> pastValues;
    private List<Cell> dependsOnValues;
    private List<Cell> influencingOnValues;
    private String cellId;

    // Constructor
    public CellImpl(int row, int column, String originalValue) {
        this.coordinate = new CoordinateImpl(row, column);
        this.originalValue = originalValue;
        this.version = 0;
        this.dependsOnValues = new ArrayList<>();
        this.influencingOnValues = new ArrayList<>();
        cellId = rowToString(row) + "" + (column+1);
        if (originalValue != null) {
            updateVersion();
            calculateEffectiveValue();
        }
    }

//    @Override
//    public CellImpl clone() {
//        try {
//            CellImpl cloned = (CellImpl) super.clone();
//            // Deep copy if necessary (e.g., lists)
//            cloned.pastValues = new ArrayList<>(this.pastValues);
//            cloned.dependsOnValues = new ArrayList<>(this.dependsOnValues);
//            cloned.influencingOnValues = new ArrayList<>(this.influencingOnValues);
//            // The expression might also need cloning, depending on its immutability
//            return cloned;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError(); // Can't happen
//        }
//    }

    private char rowToString(int row) {
        return (char)('A' + (row));
    }

    @Override
    public String getCellId() {
        return cellId;
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
    public void setCellOriginalValue(String value) {
        this.originalValue = value;
    }

    @Override
    public Expression<?> getEffectiveValue() {
        return effectiveValue;
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
    public List<Cell> getPastValues() {
        return pastValues;
    }

    public void addPastValue(Cell value) {
        pastValues.add(value);
    }

    @Override
    public List<Cell> getDependsOnValues() {
        return dependsOnValues;
    }

    public void addDependsOnValue(Cell value) {
        dependsOnValues.add(value);
    }

    @Override
    public List<Cell> getInfluencingOnValues() {
        return influencingOnValues;
    }

    public void addInfluencingOnValues(Cell value) {
        influencingOnValues.add(value);
    }

    public Expression<?> getContent() {
        if (effectiveValue != null) {
            return effectiveValue;
        }
        return null;
    }

    @Override
    public void calculateEffectiveValue() {
        // build the expression object out of the original value...
        // it can be {PLUS, 4, 5} OR {CONCAT, "hello", "world"}
        Expression<?> expression = ExpressionFactory.createExpression(originalValue);
        this.effectiveValue = ExpressionFactory.createExpression(expression.evaluate().toString());
    }
}

