package expression;

public class Text implements Expression<String> {

    private final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public String evaluate() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}