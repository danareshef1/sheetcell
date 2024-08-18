//package parser;
//
//import expression.BinaryExpression;
//import expression.Expression;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Parameter;
//import java.util.List;
//
//public class BinaryExpressionParser<T extends BinaryExpression<S>, S> extends FunctionParser<T> {
//
//    private final Class<T> expressionClass;
//
//
//    public BinaryExpressionParser(Class<T> expressionClass, String name) {
//        super(name);
//        this.expressionClass = expressionClass;
//    }
//
//    public T createBinaryExpression(Expression<?> left, Expression<?> right) {
//        Constructor<?>[] constructors = expressionClass.getDeclaredConstructors();
//
//        if (constructors.length == 0) {
//            throw new RuntimeException("No constructor found for " + expressionClass);
//        }
//
//        Parameter[] parameters = constructors[0].getParameters();
//        Class<?> leftType = parameters[0].getType();
//        Class<?> rightType = parameters[1].getType();
//
//        if (!leftType.isInstance(left) || !rightType.isInstance(right)) {
//            throw new RuntimeException("Invalid type for " + expressionClass);
//        }
//
//        try {
//            return expressionClass.getConstructor(leftType, rightType).newInstance((leftType.cast(left)), rightType.cast(right));
//        }catch (Exception e){
//            throw new RuntimeException("Failed to create instance of " + expressionClass.getSimpleName(), e);
//        }
//    }
//
//    @Override
//    protected T createExpression(List<String> args)
//    {
//        if (args.size() != 2){
//            throw new IllegalArgumentException(functionName + "Invalid number of arguments, must be exactly 2");
//        }
//
//        Expression<?> left = ExpressionFactory.createExpression(args.getFirst().trim());
//        Expression<?> right = ExpressionFactory.createExpression(args.getLast().trim());
//        return createBinaryExpression(left, right);
//    }
//}
