import expression.Expression;
import expression.Number;
import expression.Text;
import expression.numericalExpression.*;
import expression.stringExpression.CONCAT;
import expression.stringExpression.SUB;

public class Main {
    public static void main(String[] args) {
        Expression<Double> ex1 = new MINUS(new Number(5.0), new DIVIDE(new Number(10.0), new Number(0)));
        Expression<String> ex2 = new SUB(new Text("ABCD"), new PLUS(new Number(0), new Number(0)), new Number(5));
        Expression<String> ex3 = new CONCAT(new Text("A"), new Text("B"));

        Double result = ex1.evaluate();
        System.out.println(result);

        System.out.println(ex2.evaluate());
        System.out.println(ex3.evaluate());
    }
}