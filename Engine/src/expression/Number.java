package expression;

public class Number implements Expression<Double> {

    private final double num;

    public Number(double num) {
        this.num = num;
    }

    @Override
    public Double evaluate() {
        return num;
    }

    @Override
    public String toString() {
        return Double.toString(num);
    }
}