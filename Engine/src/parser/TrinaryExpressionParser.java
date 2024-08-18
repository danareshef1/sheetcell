//package parser;
//
//import expression.BinaryExpression;
//import expression.Expression;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Parameter;
//import java.util.List;
//
//public class TrinaryExpressionParser<T extends BinaryExpression<S>, S> extends FunctionParser<T> {
//
//    private final Class<T> expressionClass;
//
//
//    public TrinaryExpressionParser(Class<T> expressionClass, String name) {
//        super(name);
//        this.expressionClass = expressionClass;
//    }
//
//    public T createTrinaryExpression(Expression<?> first, Expression<?> second, Expression<?> third) {
//        Constructor<?>[] constructors = expressionClass.getDeclaredConstructors();
//
//        if (constructors.length == 0) {
//            throw new RuntimeException("No constructor found for " + expressionClass);
//        }
//
//        Parameter[] parameters = constructors[0].getParameters();
//        Class<?> firstType = parameters[0].getType();
//        Class<?> secondType = parameters[1].getType();
//        Class<?> thirdType = parameters[2].getType();
//
//        if (!firstType.isInstance(first) || !secondType.isInstance(second) || !thirdType.isInstance(third)) {
//            throw new RuntimeException("Invalid type for " + expressionClass);
//        }
//
//        try {
//            return expressionClass.getConstructor(firstType, secondType, thirdType).newInstance((firstType.cast(first)),
//                    secondType.cast(second), thirdType.cast(third));
//        }catch (Exception e){
//            throw new RuntimeException("Failed to create instance of " + expressionClass.getSimpleName(), e);
//        }
//    }
//
//    @Override
//    protected T createExpression(List<String> args)
//    {
//        if (args.size() != 3){
//            throw new IllegalArgumentException(functionName + "Invalid number of arguments, must be exactly 2");
//        }
//
//        Expression<?> first = ExpressionFactory.createExpression(args.getFirst().trim());
//        Expression<?> second = ExpressionFactory.createExpression(args.get(1).trim());
//        Expression<?> third = ExpressionFactory.createExpression(args.getLast().trim());
//        return createTrinaryExpression(first, second, third);
//    }
//}
