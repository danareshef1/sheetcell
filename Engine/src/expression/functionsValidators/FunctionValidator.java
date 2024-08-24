//package expression;
//
//import expression.numericalExpression.PLUS;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FunctionValidator {
//
//    protected String functionName;
//
//    public String[] functionParts(String expression){
//        expression = expression.trim();
//
//        functionName = getFunctionName(expression);
//        if (expression.startsWith("{") && expression.endsWith("}")) {
//            expression = expression.substring(1, expression.length() - 1).trim();
//        } else {
//            throw new IllegalArgumentException("Invalid expression format: " + expression);
//        }
//
//        String[] parts = expression.split(",");
//
//        if (!parts[0].trim().equals(functionName)) {
//            throw new IllegalArgumentException("Invalid" + functionName + "expression format: " + expression);
//        }
//
//        return parts;
//    }
//
//    public static String getFunctionName(String expression) {
//        expression = expression.substring(1, expression.length() - 1).trim();
//        String[] parts = expression.split(",", 2);
//        return parts[0].trim();
//    }
//}

package expression.functionsValidators;

import sheet.SheetReadActions;

import java.util.ArrayList;
import java.util.List;

public class FunctionValidator {

    public String functionName;

    public String[] functionParts(String expression) {
        expression = expression.trim();

        if (expression.startsWith("{") && expression.endsWith("}")) {
            expression = expression.substring(1, expression.length() - 1).trim();
        } else {
            throw new IllegalArgumentException("Invalid expression format: " + expression);
        }

        List<String> parts = getStringList(expression);
        String thisFuncName = parts.getFirst().trim().toUpperCase();

        // Check the function name after splitting
        if (parts.isEmpty() || !thisFuncName.equals(functionName)) {
            throw new IllegalArgumentException("Invalid " + functionName + " expression format: " + expression);
        }

        parts.getFirst().toUpperCase().trim();
        return parts.toArray(new String[0]);
    }

    private static List<String> getStringList(String expression) {
        List<String> parts = new ArrayList<>();
        int braceCount = 0;
        StringBuilder currentPart = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '{') {
                braceCount++;
            } else if (c == '}') {
                braceCount--;
            }

            if (c == ',' && braceCount == 0) {
                parts.add(currentPart.toString().trim());
                currentPart = new StringBuilder();
            } else {
                currentPart.append(c);
            }
        }

        parts.add(currentPart.toString().trim());
        return parts;
    }

    public static String getFunctionName(String expression) {
        expression = expression.substring(1, expression.length() - 1).trim();
        String[] parts = expression.split(",", 2);
        return parts[0].trim().toUpperCase();
    }
}

