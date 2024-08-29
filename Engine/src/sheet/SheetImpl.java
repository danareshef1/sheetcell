package sheet;

import sheet.cell.Cell;
import sheet.cell.CellImpl;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;
import sheet.layout.Layout;
import sheet.version.Version;
import sheet.version.VersionImpl;
import java.io.*;
import java.util.*;

public class SheetImpl implements Sheet {
    private Version version;
    private final String name;
    private Map<Coordinate,Cell> activeCells;
    private final Layout size;
    private int countChangedCells;
    private Cell currentCalculatingCell;

    public SheetImpl(String name, Layout size, int countChangedCells) {
        this.version = new VersionImpl();
        this.name = name;
        this.size = size;
        this.activeCells = new HashMap<>();
        this.countChangedCells = countChangedCells;
        this.currentCalculatingCell = null;
    }

    @Override
    public int getCountChangedCells(){
        return countChangedCells;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Layout getSheetSize() {
        return size;
    }

    @Override
    public Cell getCell(int row, int column) {
        sheetBoundsCheck(row, column);

        if(activeCells.get(CoordinateFactory.createCoordinate(row, column)) == null){
            return new CellImpl(row, column, null, 0, this);
        }
        return activeCells.get(CoordinateFactory.createCoordinate(row, column));
    }

    public void sheetBoundsCheck(int row, int column) {
        if (row > size.getNumRows() || column > size.getNumCols()) {
            throw new IllegalArgumentException("Those coordinates are out of the sheet's bounds.");
        }
    }

    public SheetImpl updateCellValueAndCalculate(int row, int column, String value, boolean first) {
        Coordinate coordinate = CoordinateFactory.createCoordinate(row, column);
        SheetImpl newSheetVersion = this;
        if(!first) {
            newSheetVersion = copySheet();
        }
        newSheetVersion.updateOrCreateCell(coordinate, row, column, value, newSheetVersion,first);
        int numOfCellsThatHaveChanged = 1;
        if(!first){
            newSheetVersion.handleEmptyCell(coordinate, value, newSheetVersion);
        }
        try {
            List<Cell> cellsThatHaveChanged = newSheetVersion.recalculateAndGetChangedCells();
            if (!first) {
                newSheetVersion.incrementVersion();
            }
            cellsThatHaveChanged.forEach(Cell::updateVersion);
            numOfCellsThatHaveChanged += cellsThatHaveChanged.size();
            if (!first) {
                newSheetVersion.countChangedCells = numOfCellsThatHaveChanged;
            }
            return newSheetVersion;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<Cell> recalculateAndGetChangedCells(){
        List<Cell> cellsThatHaveChanged = new ArrayList<>();

        for (Cell cell : orderCellsForCalculation()) {
            setCurrentCalculatingCell(cell);

            if (cell.calculateEffectiveValue()) {
                cellsThatHaveChanged.add(cell);
            }
        }

        return cellsThatHaveChanged;
    }

    @Override
    public void setCurrentCalculatingCell(Cell cell) {
        this.currentCalculatingCell = cell;
    }

    @Override
    public Cell getCurrentCalculatingCell() {
        return this.currentCalculatingCell;
    }

    private void handleEmptyCell(Coordinate coordinate, String value, SheetImpl sheet) {
        if(value.isEmpty()){
            removeCoordinate(coordinate, sheet);
        }
    }

    public void updateOrCreateCell(Coordinate coordinate, int row, int column, String value, SheetImpl newSheetVersion
                                        ,boolean first) {
        Cell cell = activeCells.get(coordinate);
        int newVersionNumber = newSheetVersion.getVersion();
        newVersionNumber += !first ? 1 : 0;

        if (cell != null) {
            if(!first) {
                cell.removeDependencies();
            }
            cell.setCellOriginalValue(value,first);
            cell.setVersion(newVersionNumber);
            cell.calculateEffectiveValue();
        } else {
            cell = new CellImpl(row, column, value, newVersionNumber, this);
            activeCells.put(coordinate, cell);
        }
    }

    private void removeCoordinate(Coordinate coordinate, SheetImpl sheet) {
        Cell cell = activeCells.get(coordinate);
        int version = sheet.getVersion()+1;

        if(cell != null) {
            cell.removeDependencies();
        }
        cell = new CellImpl(coordinate.getRow(), coordinate.getColumn(), null, version, this);
        activeCells.put(coordinate, cell);
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