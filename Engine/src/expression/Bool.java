package expression;

public class Bool implements Expression<Boolean>{
    private final boolean num;

    public Bool(boolean num) {
        this.num = num;
    }

    @Override
    public Boolean evaluate() {
        return num;
    }

    @Override
    public String toString() {
        return Boolean.toString(num);
    }
}
