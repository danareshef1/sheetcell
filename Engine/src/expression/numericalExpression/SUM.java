//package expression.numericalExpression;
//
//import expression.Expression;
//import expression.functionsValidators.FunctionValidator;
//import expression.systemicExpression.REF;
//import parser.ExpressionParser;
//import sheet.SheetReadActions;
//import sheet.cell.CellType;
//import sheet.cell.EffectiveValue;
//import sheet.cell.EffectiveValueImpl;
//import sheet.range.Range;
//import sheet.range.RangeManager;
//
//import java.util.Set;
//
//public class SUM extends FunctionValidator implements NumericalExpression, ExpressionParser<Expression> {
//    private final Range range;
//
//    public SUM(Range range) {
//        this.range = range;
//        this.functionName = "SUM";
//    }
//
////    @Override
////    public EffectiveValue evaluate(SheetReadActions sheet) {
////        EffectiveValue rangeValue = expression.evaluate(sheet);
////
////        if (rangeValue == null || !(rangeValue.getValue() instanceof List<?> valuesInRange)) {
////            throw new IllegalArgumentException("This range does not exist in this sheet.");
////        }
////
////        double sum = 0;
////        boolean hasNumericValue = false;
////
////        for (Object value : valuesInRange) {
////            if (value instanceof EffectiveValue effectiveValue) {
////                if (effectiveValue.getValue() instanceof Number) {
////                    sum += ((Number) effectiveValue.getValue()).doubleValue();
////                    hasNumericValue = true;
////                }
////            }
////        }
////
////        if (!hasNumericValue) {
////            return new EffectiveValueImpl(CellType.NUMERIC, 0);
////        }
////
////        return new EffectiveValueImpl(CellType.NUMERIC, sum);
////    }
//
//    @Override
//    public EffectiveValue evaluate(SheetReadActions sheet) throws IllegalArgumentException {
//
//        Set<REF> refOfRange = range.getRangeRefs();
//        double sum = 0.0;
//
//        for(REF ref : refOfRange) {
//            EffectiveValue value = ref.evaluate(sheet);
//            try {
//                sum += (double) value.getValue();
//            } catch (Exception e) {
//                throw new IllegalArgumentException("Evaluation failed.");
//            }
//        }
//
//        return new EffectiveValueImpl(CellType.NUMERIC, sum);
//    }
//
////    @Override
////    public EffectiveValue evaluate(SheetReadActions sheet) {
////        EffectiveValue rangeValue = range.evaluate(sheet);
////
////        if (rangeValue == null || !(rangeValue.getValue() instanceof String rangeName)) {
////            throw new IllegalArgumentException("Invalid range name or evaluation failed.");
////        }
////
////        // Resolve the range name to actual cell values
////        List<Cell> cellsInRange = resolveRange(sheet, rangeName);
////
////        double sum = 0;
////        boolean hasNumericValue = false;
////
////        for (Cell cell : cellsInRange) {
////            EffectiveValue effectiveValue = cell.getEffectiveValue();
////            if (effectiveValue != null && effectiveValue.getValue() instanceof Number) {
////                sum += ((Number) effectiveValue.getValue()).doubleValue();
////                hasNumericValue = true;
////            }
////        }
////
////        if (!hasNumericValue) {
////            return new EffectiveValueImpl(CellType.NUMERIC, 0);
////        }
////
////        return new EffectiveValueImpl(CellType.NUMERIC, sum);
////    }
////
////    private List<Cell> resolveRange(SheetReadActions sheet, String rangeName) {
////        // Implement logic to resolve range name to actual cells
////        // This may involve parsing the rangeName and retrieving cells accordingly
////        // Example:
////        // return sheet.getCellsInRange(rangeName);
////        return Collections.emptyList(); // Placeholder
////    }
//
//    @Override
//    public CellType getFunctionResultType() {
//        return CellType.NUMERIC;
//    }
//
//    @Override
//    public Expression parse(Expression... args) {
//        if (args.length != 1) {
//            throw new IllegalArgumentException("SUM function requires exactly 1 argument, but got " + args.length);
//        }
//
//        Range range1 = RangeManager.getRange(args[0].toString().toUpperCase());
//
//        return new SUM(range1);
//    }
//
////    public String toString(SheetReadActions sheet) {
////        return "{" + functionName + "," + expression.evaluate(sheet) + "}";
////    }
//
//    private int getRowFromCellId(String cellId) {
//        return cellId.charAt(1);
//    }
//
//    private int getColFromCellId(String cellId) {
//        return (cellId.charAt(0) - 'A');
//    }
//}

package expression.numericalExpression;

import expression.Expression;
import expression.functionsValidators.FunctionValidator;
import expression.systemicExpression.REF;
import parser.ExpressionParser;
import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;
import sheet.range.Range;

import java.util.Set;

public class SUM extends FunctionValidator implements NumericalExpression, ExpressionParser<Expression> {
    private final Expression expression;

    public SUM(Expression expression) {
        this.expression = expression;
        this.functionName = "SUM";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) throws IllegalArgumentException {
        EffectiveValue rangeValue = expression.evaluate(sheet);

        if (rangeValue == null || !(rangeValue.getValue() instanceof Range range)) {
            throw new IllegalArgumentException("This expression does not resolve to a valid range.");
        }

        Set<REF> refOfRange = range.getRangeRefs();
        double sum = 0.0;
        boolean hasNumericValue = false;

        for (REF ref : refOfRange) {
            EffectiveValue value = ref.evaluate(sheet);
            if (value != null && value.getValue() instanceof Number) {
                sum += ((Number) value.getValue()).doubleValue();
                hasNumericValue = true;
            }
        }

        if (!hasNumericValue) {
            return new EffectiveValueImpl(CellType.NUMERIC, 0);
        }

        return new EffectiveValueImpl(CellType.NUMERIC, sum);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }

    @Override
    public Expression parse(Expression... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("SUM function requires exactly 1 argument, but got " + args.length);
        }

        return new SUM(args[0]);
    }

//    private int getRowFromCellId(String cellId) {
//        return cellId.charAt(1);
//    }
//
//    private int getColFromCellId(String cellId) {
//        return (cellId.charAt(0) - 'A');
//    }
}

