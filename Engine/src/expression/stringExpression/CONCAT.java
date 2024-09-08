package expression.stringExpression;

import expression.Expression;
import expression.functionsValidators.FunctionValidator;
import expression.systemicExpression.REF;
import parser.ExpressionParser;
import sheet.SheetReadActions;
import sheet.cell.CellType;
import sheet.cell.EffectiveValue;
import sheet.cell.EffectiveValueImpl;

import static parser.ExpressionFactory.createExpression;

public class CONCAT extends FunctionValidator implements StringExpression, ExpressionParser<Expression> {
    private final Expression expression1;
    private final Expression expression2;

    public CONCAT(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.functionName = "CONCAT";
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions sheet) {
        EffectiveValue leftValue = expression1.evaluate(sheet);
        EffectiveValue rightValue = expression2.evaluate(sheet);

        // Check if both arguments are numerical expressions
        if ((!expression1.getFunctionResultType().equals(CellType.STRING) && !expression1.getFunctionResultType().equals(CellType.UNKNOWN))
                || (!expression2.getFunctionResultType().equals(CellType.STRING) && !expression2.getFunctionResultType().equals(CellType.UNKNOWN))){
            return new EffectiveValueImpl(CellType.STRING, "!UNDEFINED!");
        }

        if (checkIfRefType(expression1, sheet, CellType.STRING) || checkIfRefType(expression2, sheet, CellType.STRING)){
            return new EffectiveValueImpl(CellType.STRING, "!UNDEFINED!");
        }

        try {
            String left = leftValue.extractValueWithExpectation(String.class);
            String right = rightValue.extractValueWithExpectation(String.class);
            if(left.equals("!UNDEFINED!")  || right.equals("!UNDEFINED!")) {
                return new EffectiveValueImpl(CellType.STRING, "!UNDEFINED!");
            }
            String result = left + right;
            return new EffectiveValueImpl(CellType.STRING, result);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("The function CONCAT expecting for 2 strings.");
        }
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.STRING;
    }

    @Override
    public Expression parse(Expression... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("CONCAT function requires exactly 2 arguments, but got " + args.length);
        }

//        // Check if both arguments are numerical expressions
//        if ((!args[0].getFunctionResultType().equals(CellType.STRING) && !args[0].getFunctionResultType().equals(CellType.UNKNOWN))
//                && (!args[1].getFunctionResultType().equals(CellType.STRING) || !args[1].getFunctionResultType().equals(CellType.UNKNOWN))) {
//            throw new IllegalArgumentException("Invalid argument types for CONCAT function. Expected STRING, but got " + args[0].getFunctionResultType()
//                    + " and " + args[1].getFunctionResultType());
//        }

        return new CONCAT(args[0], args[1]);
    }

    public String toString(SheetReadActions sheet) {
        return "{" + functionName + "," + expression1.evaluate(sheet) + "," + expression2.evaluate(sheet) + "}";
    }
}