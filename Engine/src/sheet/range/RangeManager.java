package sheet.range;

import expression.systemicExpression.REF;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RangeManager {
    private static final Map<String, Range> ranges = new HashMap<>();
    private final Map<String, Set<String>> rangeUsage = new HashMap<>();

    public void addRange(String name, String topLeftCellId, String bottomRightCellId) throws IllegalArgumentException {
        if (ranges.containsKey(name)) {
            throw new IllegalArgumentException("Range name already exists. Please choose a different name.");
        }
        // Generate the set of REF objects for the range
        Set<REF> rangeRefs = generateRangeRefs(topLeftCellId, bottomRightCellId);
        Range newRange = new Range(rangeRefs, name);

        ranges.put(name, newRange);
        rangeUsage.put(name, new HashSet<>());
    }

    // Helper method to generate REF objects between top-left and bottom-right cells
    private Set<REF> generateRangeRefs(String topLeftCellId, String bottomRightCellId) {
        Set<REF> refs = new HashSet<>();

        // Logic to determine all cell references between topLeftCellId and bottomRightCellId
        // Assuming REF has a constructor that takes a cell ID and other needed parameters

        Coordinate topLeft = CoordinateFactory.cellIdToRowCol(topLeftCellId);
        Coordinate bottomRight = CoordinateFactory.cellIdToRowCol(bottomRightCellId);

        // Iterate over the range from topLeft to bottomRight
        for (int row = topLeft.getRow(); row <= bottomRight.getRow(); row++) {
            for (int col = topLeft.getColumn(); col <= bottomRight.getColumn(); col++) {
                // Generate a REF for each cell in the range
                Coordinate cellId = CoordinateFactory.createCoordinate(row, col);
                REF ref = new REF(cellId); // Adjust parameters based on REF constructor
                refs.add(ref);
            }
        }

        return refs;
    }

    public void deleteRange(String name) throws IllegalArgumentException {
        if (!ranges.containsKey(name)) {
            throw new IllegalArgumentException("Range not found.");
        }
        if (!rangeUsage.get(name).isEmpty()) {
            throw new IllegalArgumentException("Cannot delete range because it is used in existing functions.");
        }
        ranges.remove(name);
        rangeUsage.remove(name);
    }

    public static Range getRange(String name) throws IllegalArgumentException {
        if (!ranges.containsKey(name)) {
            throw new IllegalArgumentException("Range not found.");
        }
        return ranges.get(name);
    }

    public void addRangeUsage(String rangeName, String functionName) {
        if (ranges.containsKey(rangeName)) {
            rangeUsage.get(rangeName).add(functionName);
        }
    }

    public void removeRangeUsage(String rangeName, String functionName) {
        if (ranges.containsKey(rangeName)) {
            rangeUsage.get(rangeName).remove(functionName);
        }
    }

    public void highlightRange(Range range) {
        // לוגיקה להדגשת תאים במסך UI לפי הצורך
        //System.out.println("Highlighting range: " + range);
    }
}
