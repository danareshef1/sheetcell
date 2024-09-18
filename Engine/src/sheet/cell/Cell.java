package sheet.cell;

import sheet.coordinate.Coordinate;
import java.io.Serializable;
import java.util.Set;

public interface Cell extends Serializable {
    Coordinate getCoordinate();
    String getOriginalValue();
    void setCellOriginalValue(String value, boolean first);
    EffectiveValue getEffectiveValue();
    void addInfluencingOnValues(sheet.cell.Cell cell);
    void setVersion(int version);
    boolean calculateEffectiveValue();
    int getVersion();
    Set<sheet.cell.Cell> getDependsOnValues();
    void addDependsOnValue(sheet.cell.Cell cell);
    Set<sheet.cell.Cell> getInfluencingOnValues();
    void updateVersion();
    String getCellId();
    void removeDependencies();
}