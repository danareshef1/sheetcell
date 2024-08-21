package parser;

public class StringValidator {

    public static boolean isNumber(String expression) {
        if (expression == null || expression.isEmpty()) {
            return false;
        }

        try {
            Double.parseDouble(expression);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBool(String expression) {
        if (expression == null) {
            return false;
        }

        String trimmedExpression = expression.trim().toLowerCase();
        return "true".equals(trimmedExpression) || "false".equals(trimmedExpression);
    }

    public static boolean isFunction(String expression) {
        if (expression == null || expression.isEmpty()) {
            return false;
        }

        return expression.matches("\\{[A-Za-z_]+\\s*,\\s*(?:(?:[^{}]+)|(?:\\{[^{}]*\\}))+\\}");
    }
}

