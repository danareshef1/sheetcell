//package expression.numericalExpression;
//
//import expression.Expression;
//import expression.functionsValidators.FunctionValidator;
//import parser.ExpressionParser;
//import sheet.SheetReadActions;
//import sheet.cell.Cell;
//import sheet.cell.CellType;
//import sheet.cell.EffectiveValue;
//import sheet.cell.EffectiveValueImpl;
//
//import java.util.Collections;
//import java.util.List;
//
//public class AVERAGE extends FunctionValidator implements NumericalExpression, ExpressionParser<Expression> {
//    private final Expression expression;
//
//    public AVERAGE(Expression expression) {
//        this.expression = expression;
//        this.functionName = "AVERAGE";
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
////        int count = 0;
////
////        for (Object value : valuesInRange) {
////            if (value instanceof EffectiveValue effectiveValue) {
////                if (effectiveValue.getValue() instanceof Number) {
////                    sum += ((Number) effectiveValue.getValue()).doubleValue();
////                    count++;
////                }
////            }
////        }
////
////        if (count == 0) {
////            throw new IllegalArgumentException("The range does not include any numeric cells.");
////        }
////
////        double average = sum / count;
////        return new EffectiveValueImpl(CellType.NUMERIC, average);
////    }
//
//    @Override
//    public EffectiveValue evaluate(SheetReadActions sheet) {
//        // Evaluate the expression to get the range name
//        EffectiveValue rangeValue = expression.evaluate(sheet);
//        if (rangeValue == null || !(rangeValue.getValue() instanceof String rangeName)) {
//            throw new IllegalArgumentException("Invalid range name or evaluation failed.");
//        }
//
//        // Resolve the range name to actual cell values
//        List<Cell> cellsInRange = resolveRange(sheet, rangeName);
//
//        double sum = 0;
//        int count = 0;
//
//        for (Cell cell : cellsInRange) {
//            EffectiveValue effectiveValue = cell.getEffectiveValue();
//            if (effectiveValue != null && effectiveValue.getValue() instanceof Number) {
//                sum += ((Number) effectiveValue.getValue()).doubleValue();
//                count++;
//            }
//        }
//
//        if (count == 0) {
//            throw new IllegalArgumentException("No numeric values found in the range.");
//        }
//
//        double average = sum / count;
//        return new EffectiveValueImpl(CellType.NUMERIC, average);
//    }
//
//    private List<Cell> resolveRange(SheetReadActions sheet, String rangeName) {
//        // Implement logic to resolve range name to actual cells
//        // Example:
//        // return sheet.getCellsInRange(rangeName);
//        return Collections.emptyList(); // Placeholder
//    }
//
//    @Override
//    public CellType getFunctionResultType() {
//        return CellType.NUMERIC;
//    }
//
//    @Override
//    public Expression parse(Expression... args) {
//        if (args.length != 1) {
//            throw new IllegalArgumentException("AVERAGE function requires exactly 1 argument, but got " + args.length);
//        }
//
//        return new AVERAGE(args[0]);
//    }
//
//    public String toString(SheetReadActions sheet) {
//        return "{" + functionName + "," + expression.evaluate(sheet) + "}";
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

public class AVERAGE extends FunctionValidator implements NumericalExpression, ExpressionParser<Expression> {
    private final Expression expression;

    public AVERAGE(Expression expression) {
        this.expression = expression;
        this.functionName = "AVERAGE";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) throws IllegalArgumentException {
        // Ensure the expression evaluates to a range or a valid set of references.
        EffectiveValue rangeValue = expression.evaluate(sheet);

        if (rangeValue == null || !(rangeValue.getValue() instanceof Range range)) {
            throw new IllegalArgumentException("This expression does not resolve to a valid range.");
        }

        Set<REF> refOfRange = range.getRangeRefs();
        double sum = 0.0;
        int count = 0;

        for (REF ref : refOfRange) {
            EffectiveValue value = ref.evaluate(sheet);
            if (value != null && value.getValue() instanceof Number) {
                sum += ((Number) value.getValue()).doubleValue();
                count++;
            }
        }

        if (count == 0) {
            throw new IllegalArgumentException("No numeric values found in the range.");
        }

        double average = sum / count;
        return new EffectiveValueImpl(CellType.NUMERIC, average);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }

    @Override
    public Expression parse(Expression... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("AVERAGE function requires exactly 1 argument, but got " + args.length);
        }

        return new AVERAGE(args[0]);
    }
}

