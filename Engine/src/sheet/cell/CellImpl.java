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
    private Expression<?> effectiveValue;
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
        this.version = 1;
        this.dependsOnValues = new ArrayList<>();
        this.influencingOnValues = new ArrayList<>();
        cellId = rowToString(row) + "" + (column+1);
        calculateEffectiveValue();
    }

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

        // first question: what is the generic type of Expression ?
        Expression<?> expression = ExpressionFactory.createExpression(originalValue);

        // second question: what is the return type of eval() ?
        this.effectiveValue = ExpressionFactory.createExpression(expression.evaluate().toString());
    }
}

