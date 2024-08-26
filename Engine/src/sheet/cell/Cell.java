package sheet.cell;

import expression.Expression;
import sheet.coordinate.Coordinate;

import java.io.Serializable;
import java.util.List;

public interface Cell extends Serializable {
    Coordinate getCoordinate();
    String getOriginalValue();
    void setCellOriginalValue(String value);
//    void setCellEffectiveValue(Expression<?> value);
    EffectiveValue getEffectiveValue();
    void addInfluencingOnValues(Coordinate cell);

    boolean calculateEffectiveValue();
    int getVersion();
    List<Cell> getDependsOnValues();
    void addDependsOnValue(Coordinate cell);
    List<Cell> getInfluencingOnValues();
    List<Cell> getPastValues();
    void updateVersion();
    String getCellId();
}
