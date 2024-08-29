package fromEngine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import sheet.cell.Cell;
import sheet.coordinate.Coordinate;

public class CellDTO {
    private String effectiveValue = null;
    private final String originalValue;
    private final int version;
    private final Set<Coordinate> dependsOnValues;
    private final Set<Coordinate> influencingOnValues;
    private final String cellId;
    private final Cell cell;

    public CellDTO(Cell cell) {
        this(cell, new HashSet<>());
    }

    private CellDTO(Cell cell, Set<String> visited) {
        this.cell = cell;
        this.cellId = cell.getCellId() != null ? cell.getCellId() : "";
        this.originalValue = cell.getOriginalValue();
        this.version = cell.getVersion();
        this.dependsOnValues = getDependsOn();
        this.influencingOnValues = getInfluencingOn();
        if (originalValue != null) {
            this.effectiveValue = cell.getEffectiveValue().getValue().toString();
        } else {
            this.effectiveValue = null;
        }
    }

    public Set<Coordinate> getDependsOn() { return extractCoordinates(cell.getDependsOnValues()); }

    public Set<Coordinate> getInfluencingOn() { return extractCoordinates(cell.getInfluencingOnValues()); }

    private Set<Coordinate> extractCoordinates(Set<Cell> cells) {
        if (cells == null) {
            return Collections.emptySet();
        }

        return cells.stream()
                .map(Cell::getCoordinate)
                .collect(Collectors.toSet());
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

    public String getCellId() {
        return cellId;
    }
}