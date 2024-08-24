//package expression;
//
//import expression.functionsValidators.FunctionValidator;
//import sheet.SheetReadActions;
//
//public abstract class TrinaryExpression extends FunctionValidator implements Expression {
//
//    private final Expression expression1;
//    private final Expression expression2;
//    private final Expression expression3;
//    protected int argsNumberForFunc = 4;
//
//    public TrinaryExpression(Expression expression1, Expression expression2, Expression expression3) {
//        this.expression1 = expression1;
//        this.expression2 = expression2;
//        this.expression3 = expression3;
//    }
//
//    @Override
//    public String toString(SheetReadActions sheet) {
//        return "{" + functionName + "," + expression1.evaluate(sheet) + "," + expression2.evaluate(sheet) + "," + expression3.evaluate(sheet) + "}";
//    }
//}
