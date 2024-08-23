package fromUI;

import engine.EngineImpl;

public class CellUpdateDTO {
    private final String cellId;
    private final String newValue;

    public CellUpdateDTO(String cellId, String newValue) {
        this.cellId = cellId;
        this.newValue = newValue;
    }

    public String getCellId() {
        return cellId;
    }

    public String getNewValue() {
        return newValue;
    }
}
