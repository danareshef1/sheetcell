//package expression;
//
//import sheet.cell.Cell;
//import sheet.Sheet;
//
//public class REF<T> extends UnaryExpression<T> {
//
//    private final String cellId;
//    private final Sheet sheet;
//
//    public REF(String cellId, Sheet sheet) {
//        super(null); // REF does not use the operand, so pass null
//        this.cellId = cellId;
//        this.sheet = sheet;
//        this.functionName = "REF";
//    }
//
//    @Override
//    protected T evaluate(T operand) {
//        throw new UnsupportedOperationException("REF does not use the operand directly for evaluation.");
//    }
//
//    @Override
//    public T evaluate() {
//        try {
//            // Extract row and column from cellId
//            int row = Integer.parseInt(cellId.replaceAll("[^0-9]", "")) - 1;
//            char colChar = cellId.charAt(0);
//            int col = colChar - 'A';
//
//            // Retrieve the cell from the sheet
//            Cell<?> cell = sheet.getCells()[row][col];
//
//            // Ensure the cell's effective value is compatible with type T
//            if (cell != null) {
//                Object content = cell.getContent();
//
//                // Ensure the content is of the correct type
//                if (getType().isInstance(content)) {
//                    @SuppressWarnings("unchecked")
//                    T value = (T)content; // Cast to T, but ensure type safety
//                    return value;
//                } else {
//                    throw new IllegalArgumentException("Cell content type mismatch for: " + cellId);
//                }
//            } else {
//                throw new IllegalArgumentException("Cell not found: " + cellId);
//            }
//        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
//            throw new IllegalArgumentException("Invalid cell ID format or cell out of bounds: " + cellId, e);
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "{" + functionName + "," + cellId + "}";
//    }
//
//    // Method to get the class type of T
//    private Class<?> getType() {
//        return Object.class;
//    }
//}
