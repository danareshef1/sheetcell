package sheet.cell;

import expression.Expression;
import sheet.coordinate.Coordinate;

import java.io.Serializable;
import java.util.List;

public interface Cell extends Serializable {
    Coordinate getCoordinate();
    String getOriginalValue();
    void setCellOriginalValue(String value, boolean first);
//    void setCellEffectiveValue(Expression<?> value);
    EffectiveValue getEffectiveValue();
    void addInfluencingOnValues(Cell cell);
    void setVersion(int version);
    boolean calculateEffectiveValue();
    int getVersion();
    List<Cell> getDependsOnValues();
    void addDependsOnValue(Cell cell);
    List<Cell> getInfluencingOnValues();
    void updateVersion();
    String getCellId();
    void removeDependencies();
}
