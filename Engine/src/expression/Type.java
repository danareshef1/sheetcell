//package expression;
//
//import parser.StringValidator;
//
//public class Type {
//
//    public static String getType(Expression<?> expression) {
//        String type = "STRING";
//
//        if (StringValidator.isNumber(expression.toString()))
//            type = "DOUBLE";
//        else if (StringValidator.isBool(expression.toString()))
//            type = "BOOL";
//        else if (StringValidator.isFunction(expression.toString()))
//            type = "FUNCTION";
//
//        return type;
//    }
//}