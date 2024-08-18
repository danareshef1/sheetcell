//package parser;
//
//import expression.BinaryExpression;
//import expression.Expression;
//import expression.UnaryExpression;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Parameter;
//import java.util.List;
//
//public class UnaryExpressionParser<T extends UnaryExpression<S>, S> extends FunctionParser<T> {
//
//    private final Class<T> expressionClass;
//
//
//    public UnaryExpressionParser(Class<T> expressionClass, String name) {
//        super(name);
//        this.expressionClass = expressionClass;
//    }
//
//    public T createUnaryExpression(Expression<?> expression) {
//        Constructor<?>[] constructors = expressionClass.getDeclaredConstructors();
//
//        if (constructors.length == 0) {
//            throw new RuntimeException("No constructor found for " + expressionClass);
//        }
//
//        Parameter[] parameters = constructors[0].getParameters();
//        Class<?> expressionType = parameters[0].getType();
//
//        if (!expressionType.isInstance(expression)) {
//            throw new RuntimeException("Invalid type for " + expressionClass);
//        }
//
//        try {
//            return expressionClass.getConstructor(expressionType).newInstance((expressionType.cast(expression)));
//        }catch (Exception e){
//            throw new RuntimeException("Failed to create instance of " + expressionClass.getSimpleName(), e);
//        }
//    }
//
//    @Override
//    protected T createExpression(List<String> args)
//    {
//        if (args.size() != 1){
//            throw new IllegalArgumentException(functionName + "Invalid number of arguments, must be exactly 1");
//        }
//
//        Expression<?> expression = ExpressionFactory.createExpression(args.getFirst().trim());
//        return createUnaryExpression(expression);
//    }
//}
