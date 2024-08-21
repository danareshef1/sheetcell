package fromEngine;

import java.util.ArrayList;
import java.util.List;

import fromUI.CellUpdateDTO;
import sheet.cell.Cell;
import sheet.cell.CellImpl;

public class CellDTO {
    private String effectiveValue = null;
    private String originalValue;
    private int version;
    private List<CellDTO> dependsOnValues;
    private List<CellDTO> influencingOnValues;
    private String cellId;

    public CellDTO(Cell cell) {
        this.cellId = cell.getCellId();
        this.originalValue = cell.getOriginalValue();
        this.version = cell.getVersion();
        this.effectiveValue = cell.getEffectiveValue().toString();
        this.influencingOnValues = setDependsOnValues(cell);
        this.dependsOnValues = setInfluencingOnValues(cell);
    }

    private List<CellDTO> setDependsOnValues(Cell currentCell) {
        List<CellDTO> dependsOnValues = new ArrayList<>();
        for (Cell cell : currentCell.getDependsOnValues()){
            dependsOnValues.add(new CellDTO(cell));
        }
        return dependsOnValues;
    }

    private List<CellDTO> setInfluencingOnValues(Cell currentCell) {
        List<CellDTO> influencingOnValues = new ArrayList<>();
        for (Cell cell : currentCell.getInfluencingOnValues()){
            influencingOnValues.add(new CellDTO(cell));
        }
        return influencingOnValues;
    }

    public static CellDTO createCellDTO(Cell cell) {
        if (cell == null) {
            return null;
        }
        return new CellDTO(cell);
    }

    public String getContent() {
        return effectiveValue;
    }
    public String getOriginalValue() {
        return originalValue;
    }
    public int getVersion() {
        return version;
    }
    public List<CellDTO> getDependsOnValues() {
        return dependsOnValues;
    }
    public List<CellDTO> getInfluencingOnValues() {
        return influencingOnValues;
    }
    public String getCellId() {
        return cellId;
    }

    public void updateVersion() {
        this.version++;
    }

    public CellImpl updateCell(int row, int col) {
        String cellId = CellUpdateDTO.getCellId();

    }
}
