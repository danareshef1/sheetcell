//package expression;
//
//import expression.functionsValidators.FunctionValidator;
//import sheet.SheetReadActions;
//
//public abstract class UnaryExpression extends FunctionValidator implements Expression {
//
//    private final Expression expression1;
//    private final int argsNumberForFunc = 2;
//
//    public UnaryExpression(Expression expression1) {
//        this.expression1 = expression1;
//    }
//
//    @Override
//    public String toString(SheetReadActions sheet) {
//        return "{" + functionName + "," + expression1.evaluate(sheet) + "}";
//    }
//}