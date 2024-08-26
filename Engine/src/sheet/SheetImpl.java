package sheet;

import sheet.cell.Cell;
import sheet.cell.CellImpl;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;
import sheet.layout.LayoutImpl;
import sheet.version.VersionImpl;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SheetImpl implements Sheet {
    private VersionImpl version;
    private final String name;
    private Map<Coordinate,Cell> activeCells;
    private final LayoutImpl size;

    public SheetImpl(String name, LayoutImpl size) {
        this.version = new VersionImpl();
        this.name = name;
        this.size = size;
        this.activeCells = new HashMap<>();
    }

    @Override
    public Map<Coordinate,Cell> getActiveCells() {
        return activeCells;
    }

    @Override
    public int getVersion() {
        return version.getVersionNumber();
    }

    @Override
    public void incrementVersion() {
        version.incrementVersion();
        //saveVersion();
    }

    public int getCellChangedNumber() {
        return version.getCellsChanged();
    }

    public void incrementCellChanged() {
        version.incrementCellChanged();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LayoutImpl getSheetSize() {
        return size;
    }

    @Override
    public Cell getCell(int row, int column) {
        sheetBoundsCheck(row, column);

        if(activeCells.get(CoordinateFactory.createCoordinate(row, column)) == null){
            SheetImpl newSheetVersion = copySheet();
            return new CellImpl(row, column, null, 0, newSheetVersion);
        }
        return activeCells.get(CoordinateFactory.createCoordinate(row, column));
    }
//
//    @Override
//    public Cell getCell(int row, int column) {
//        return activeCells.get(CoordinateFactory.createCoordinate(row, column));
//    }

    public void sheetBoundsCheck(int row, int column) {
        if (row > size.getNumRows() || column > size.getNumCols()) {
            throw new IllegalArgumentException("Those coordinates are out of the sheet's bounds.");
        }
    }

    @Override
    public void setCell(int row, int column, String value) {
        sheetBoundsCheck(row, column);

        Coordinate coordinate = CoordinateFactory.createCoordinate(row, column);
        SheetImpl newSheetVersion = copySheet();
        Cell cell = activeCells.get(coordinate);
        if (cell == null) {
            cell = new CellImpl(row, column, value, 0, newSheetVersion);
            activeCells.put(coordinate, cell);
        }
        cell.setCellOriginalValue(value);
        cell.updateVersion();
    }

    @Override
    public void addCell(Coordinate coordinate, Cell cell) {
        activeCells.put(coordinate, cell);
    }

    @Override
    public Sheet updateCellValueAndCalculate(int row, int column, String value) {

        Coordinate coordinate = CoordinateFactory.createCoordinate(row, column);

        Cell newCell;
        SheetImpl newSheetVersion = copySheet();
        if (newSheetVersion.activeCells.get(coordinate) != null) {
            int newVersionValue = newSheetVersion.activeCells.get(coordinate).getVersion() + 1;
            newCell = new CellImpl(row, column, value, newVersionValue, newSheetVersion);
        }
        else {
            newCell = new CellImpl(row, column, value, 1, newSheetVersion);
        }
        newSheetVersion.addCell(coordinate, newCell);
        if (value.isEmpty()) {
            newSheetVersion.activeCells.remove(coordinate);
        }
        try {
            List<Cell> cellsThatHaveChanged = newSheetVersion
                    .orderCellsForCalculation()
                    .stream()
                    .filter(Cell::calculateEffectiveValue)
                    .collect(Collectors.toList());

            cellsThatHaveChanged.forEach(Cell::updateVersion);
            newSheetVersion.incrementVersion();

            return newSheetVersion;
        } catch (Exception e) {
            throw new RuntimeException("Cant update Cell Value. This cell effects on those other cells: "
                    + newCell.getInfluencingOnValues()/*.forEach(Cell::getCellId)*/ + ". Please check first in those cells why you can't make this change " +
                    "(maybe there is a function inside one cell witch can not work with this new value).");
        }
    }

    private List<Cell> orderCellsForCalculation() {
        // Build dependency graph
        Map<Cell, List<Cell>> graph = new HashMap<>();
        Map<Cell, Integer> inDegree = new HashMap<>();
        List<Cell> result = new ArrayList<>();

        // Initialize graph and in-degree map
        for (Cell cell : activeCells.values()) {
            graph.put(cell, new ArrayList<>());
            inDegree.put(cell, 0);
        }

        // Build graph
        for (Cell cell : activeCells.values()) {
            for (Cell dependency : cell.getDependsOnValues()) {
                List<Cell> val = graph.get(dependency);
                if (val != null) {
                    val.add(cell);
                    inDegree.put(cell, inDegree.get(cell) + 1);
                }
            }
        }

        // Perform topological sort using Kahn's algorithm
        Queue<Cell> queue = new LinkedList<>();
        for (Map.Entry<Cell, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            result.add(cell);
            for (Cell neighbor : graph.get(cell)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Check for circular dependencies
        if (result.size() != activeCells.size()) {
            throw new RuntimeException("Circular dependency detected.");
        }

        return result;
    }

    @Override
    public SheetImpl copySheet() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            return (SheetImpl) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy sheet.");
        }
    }
}

