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

//    @Override
//    public void setCell(int row, int column, String value) {
//        sheetBoundsCheck(row, column);
//
//        Coordinate coordinate = CoordinateFactory.createCoordinate(row, column);
//        Cell cell = activeCells.get(coordinate);
//        if (cell == null) {
//            cell = new CellImpl(row, column, value);
//            activeCells.put(coordinate, cell);
//        }
//        cell.setCellOriginalValue(value);
//        cell.updateVersion();
//    }

    @Override
    public void addCell(Coordinate coordinate, Cell cell) {
        activeCells.put(coordinate, cell);
    }

    @Override
    public Sheet updateCellValueAndCalculate(int row, int column, String value) {

        Coordinate coordinate = CoordinateFactory.createCoordinate(row, column);

        SheetImpl newSheetVersion = copySheet();
        int newVersionValue = newSheetVersion.getVersion()+1;
        Cell newCell = new CellImpl(row, column, value, newVersionValue, newSheetVersion);
        newSheetVersion.addCell(coordinate, newCell);
        if (value.isEmpty()){
            newSheetVersion.activeCells.remove(coordinate);
        }
        try {
            List<Cell> cellsThatHaveChanged = newSheetVersion
                            .orderCellsForCalculation()
                            .stream()
                            .filter(Cell::calculateEffectiveValue)
                            .collect(Collectors.toList());

            // successful calculation. update sheet and relevant cells version
            ///int newVersion = newSheetVersion.increaseVersion();
            //cellsThatHaveChanged.forEach(cell -> cell.updateVersion(newVersion));
            //cellsThatHaveChanged.forEach(Cell::updateVersion);
            newCell.updateVersion();
            newSheetVersion.incrementVersion();

            return newSheetVersion;
        } catch (Exception e) {
            throw new RuntimeException("Error updating cell value");
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
                graph.get(dependency).add(cell);
                inDegree.put(cell, inDegree.get(cell) + 1);
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


    private SheetImpl copySheet() {
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

