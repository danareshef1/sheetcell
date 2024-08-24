//package expression;
//
//import expression.functionsValidators.FunctionValidator;
//import sheet.SheetReadActions;
//
//public abstract class BinaryExpression extends FunctionValidator implements Expression {
//
//    private final Expression expression1;
//    private final Expression expression2;
//    protected final int argsNumberForFunc = 3;
//
//    public BinaryExpression(Expression expression1, Expression expression2) {
//        this.expression1 = expression1;
//        this.expression2 = expression2;
//    }
//
//    @Override
//    public String toString(SheetReadActions sheet) {
//        return "{" + functionName + "," + expression1.evaluate(sheet) + "," + expression2.evaluate(sheet) + "}";
//    }
//}
