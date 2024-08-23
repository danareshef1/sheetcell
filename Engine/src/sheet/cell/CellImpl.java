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
    private Expression<?> effectiveValue = null;
    private String originalValue;
    private int version;
    private List<Cell> pastValues;
    private List<Cell> dependsOnValues;
    private List<Cell> influencingOnValues;
    private String cellId;
    private final SheetReadActions sheet;

    // Constructor
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

    private char columnToString(int col) {
        return (char)('A' + (col));
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
        if (originalValue != null) {
            calculateEffectiveValue();
        }
        else{
            effectiveValue = null;
        }
    }

    @Override
    public Expression<?> getEffectiveValue() {
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
    public boolean calculateEffectiveValue() {
        // build the expression object out of the original value...
        // it can be {PLUS, 4, 5} OR {CONCAT, "hello", "world"}
        Expression<?> expression = ExpressionFactory.createExpression(originalValue);
        Expression<?> newExpression = ExpressionFactory.createExpression(expression.evaluate().toString());

        if(newExpression.equals(expression)){
            return false;
        }
        else {
            this.effectiveValue = newExpression;
            return true;
        }
    }
}

