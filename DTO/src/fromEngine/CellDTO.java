package fromEngine;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sheet.cell.Cell;

public class CellDTO {
    private final StringProperty effectiveValue;
    private final StringProperty originalValue;
    private final StringProperty cellId;
    private final int version;

    public CellDTO(Cell cell) {
        this.effectiveValue = new SimpleStringProperty(cell.getEffectiveValue().getValue() != null ? cell.getEffectiveValue().getValue().toString() : "");
        this.originalValue = new SimpleStringProperty(cell.getOriginalValue());
        this.cellId = new SimpleStringProperty(cell.getCellId());
        this.version = cell.getVersion();
    }

    public StringProperty effectiveValueProperty() {
        return effectiveValue;
    }

    public StringProperty originalValueProperty() {
        return originalValue;
    }

    public StringProperty cellIdProperty() {
        return cellId;
    }

    public int getVersion() {
        return version;
    }

    public String getEffectiveValue() {
        return effectiveValue.get();
    }

    public String getOriginalValue() {
        return originalValue.get();
    }

    public String getCellId() {
        return cellId.get();
    }
    
    public static CellDTO createCellDTO(Cell cell){
        if (cell == null) {
            return null;
        }
        return new CellDTO(cell);
    }
}
