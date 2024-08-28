package sheet;

import expression.functionsValidators.FunctionValidator;
import parser.StringValidator;
import sheet.cell.Cell;
import sheet.cell.CellImpl;
import sheet.cell.EffectiveValue;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;
import sheet.layout.LayoutImpl;
import sheet.version.Version;
import sheet.version.VersionImpl;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SheetImpl implements Sheet {
    private VersionImpl version;
    private final String name;
    private Map<Coordinate,Cell> activeCells;
    private final LayoutImpl size;
    private int countChangedCells;
    private Cell currentCalculatingCell;
    @Serial
    private static final long serialVersionUID = 1L;


    public SheetImpl(String name, LayoutImpl size, int countChangedCells) {
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
            //SheetImpl newSheetVersion = copySheet();
            return new CellImpl(row, column, null, 0, this);
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
        //SheetImpl newSheetVersion = copySheet();
        Cell cell = activeCells.get(coordinate);
        if (cell == null) {
            cell = new CellImpl(row, column, value, 0, this);
            activeCells.put(coordinate, cell);
        }
        cell.setCellOriginalValue(value,false); //לא בטוח שזה באמת לא שימושי בקריאה ראשונה- לבדוק ולהתאים
        cell.updateVersion();
    }

    @Override
    public void addCell(Cell cell) {
        activeCells.put(cell.getCoordinate(), cell);
    }

    public SheetImpl updateCellValueAndCalculate(int row, int column, String value, boolean first) {
        Coordinate coordinate = CoordinateFactory.createCoordinate(row, column);
        SheetImpl newSheetVersion = this;

        if(!first) {
            newSheetVersion = copySheet();
        }

        boolean cellUpdated = newSheetVersion.updateOrCreateCell(coordinate, row, column, value, newSheetVersion,first);
        //int numOfCellsThatHaveChanged = cellUpdated ? 1 : 0;
        int numOfCellsThatHaveChanged = 1;

        if(!first){
            newSheetVersion.handleEmptyCell(coordinate, value);
        }

        try {
            List<Cell> cellsThatHaveChanged = newSheetVersion.recalculateAndGetChangedCells();

            if (!first) {
                newSheetVersion.incrementVersion();
            }
            cellsThatHaveChanged.forEach(Cell::updateVersion);
            numOfCellsThatHaveChanged += cellsThatHaveChanged.size();  ////
            newSheetVersion.countChangedCells = numOfCellsThatHaveChanged;

            return newSheetVersion;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    /*
        public void onCellUpdated(String originalValue, Coordinate coordinate) {
            countChangedCells = 0;
            String previousOriginalValue = null;
            EffectiveValue previousEffectiveValue = null;
            Cell cell = activeCells.get(coordinate);
            if (cell != null) {
                previousOriginalValue = cell.getOriginalValue();
                previousEffectiveValue = cell.getEffectiveValue();
            } else {
                cell = new CellImpl(coordinate.getRow(), coordinate.getColumn(), originalValue, version, this);
                addCell(cell);
            }

            try {
                cell.setCellOriginalValue(originalValue);
                cell.removeDependencies();
                if (cell.getEffectiveValue() == null) {

                    cell.setEffectiveValue(new EffectiveValue(coordinate));
                }
                cell.getEffectiveValue().calculateValue(this, originalValue);
                updateCells(coordinate);
                cell.setCellOriginalValue(originalValue);
                countChangedCells = 1 + cell.getInfluencingOnValues().size();
            } catch (IllegalArgumentException e){
                throw e;
            }
            catch (Exception e) {
                cell.setCellOriginalValue(previousOriginalValue);
                cell.setEffectiveValue(previousEffectiveValue);
                updateCells(coordinate);
                throw new IllegalArgumentException("Failed to update cell at " +
                        coordinate.createCellCoordinateString() + " because of " +
                        e.getMessage());

            }
        }*/
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

    private void setCurrentCalculatingCell(Cell cell) {
        if (StringValidator.isFunction(cell.getOriginalValue())) {
            if (Objects.equals("REF", FunctionValidator.getFunctionName(cell.getOriginalValue()).toUpperCase())) {
                Coordinate refCellCoordinate = CoordinateFactory.cellIdToRowCol(FunctionValidator.getCellIdForRef(cell.getOriginalValue()));
                this.currentCalculatingCell = getCell(refCellCoordinate.getRow(), refCellCoordinate.getRow());
            }
        }
    }

    private void handleEmptyCell(Coordinate coordinate, String value) {
        if(value.isEmpty()){
            removeCoordinate(coordinate);
        }
    }

    public boolean updateOrCreateCell(Coordinate coordinate, int row, int column, String value, SheetImpl newSheetVersion
                                        ,boolean first) {
        Cell cell = activeCells.get(coordinate);
        int newVersionNumber = newSheetVersion.getVersion();
        newVersionNumber += !first ? 1 : 0;

        if (cell != null) {
//            if (Objects.equals("REF", FunctionValidator.getFunctionName(cell.getOriginalValue()).toUpperCase())){
//                Coordinate refCellCoordinate = CoordinateFactory.cellIdToRowCol(FunctionValidator.getCellIdForRef(cell.getOriginalValue()));
//                Cell refCell = getCell(refCellCoordinate.getRow(), refCellCoordinate.getRow());
//            }
            if(!first) {
                cell.removeDependencies();
            }
            cell.setCellOriginalValue(value,first);
            cell.setVersion(newVersionNumber);

            return cell.calculateEffectiveValue();
        } else {
            cell = new CellImpl(row, column, value, newVersionNumber, this);
            activeCells.put(coordinate, cell);
            return true;
        }
    }

//    private void updateVersionManager(int cellsThatHaveChanged) {
//        setNumberOfChangedCellsInVersion(version, cellsThatHaveChanged);
//        setVersion(version,this);
//    }

    private void removeCoordinate(Coordinate coordinate) {
        Cell cell = activeCells.get(coordinate);
        int cellVersion = 1;

        if(cell != null) {
            cell.removeDependencies();
            cellVersion = cell.getVersion()-1;
        }
        cell = new CellImpl(coordinate.getRow(), coordinate.getColumn(), null, cellVersion, this);
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



//    private List<Cell> recalculateAndGetChangedCells(){
//        List<Cell> cellsThatHaveChanged = new ArrayList<>();
//
//        for (Cell cell : orderCellsForCalculation()) {
//            setCurrentCalculatingCell(cell);
//
//            if (cell.calculateEffectiveValue()) {
//                cellsThatHaveChanged.add(cell);
//            }
//        }
//
//        return cellsThatHaveChanged;
//    }
//
//    private void setCurrentCalculatingCell(Cell cell) {
//    }

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