package fromEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sheet.cell.Cell;

public class CellDTO {
    private String effectiveValue = null;
    private final String originalValue;
    private final int version;
    private final List<CellDTO> dependsOnValues;
    private final List<CellDTO> influencingOnValues;
    private final String cellId;

    public CellDTO(Cell cell) {
        this(cell, new HashSet<>());
    }

    private CellDTO(Cell cell, Set<String> visited) {
        this.cellId = cell.getCellId() != null ? cell.getCellId() : "";
        this.originalValue = cell.getOriginalValue();
        this.version = cell.getVersion();
        // Mark this cell as visited
        visited.add(this.cellId);
        this.dependsOnValues = Collections.unmodifiableList(setDependsOnValues(cell, visited));
        this.influencingOnValues = Collections.unmodifiableList(setInfluencingOnValues(cell, visited));
        if (originalValue != null) {
            this.effectiveValue = cell.getEffectiveValue().getValue().toString();
        } else {
            this.effectiveValue = null;
        }
    }

    private List<CellDTO> setDependsOnValues(Cell currentCell, Set<String> visited) {
        List<CellDTO> dependsOnValues = new ArrayList<>();
        for (Cell cell : currentCell.getDependsOnValues()) {
            if (!visited.contains(cell.getCellId())) {
                dependsOnValues.add(new CellDTO(cell, visited));
            }
        }
        return dependsOnValues;
    }

    private List<CellDTO> setInfluencingOnValues(Cell currentCell, Set<String> visited) {
        List<CellDTO> influencingOnValues = new ArrayList<>();
        for (Cell cell : currentCell.getInfluencingOnValues()) {
            if (!visited.contains(cell.getCellId())) {
                influencingOnValues.add(new CellDTO(cell, visited));
            }
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
}
