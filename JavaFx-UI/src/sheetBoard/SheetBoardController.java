//package sheetBoard;
//
//import fromEngine.CellDTO;
//import fromEngine.SheetDTO;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.layout.GridPane;
//import javafx.beans.property.StringProperty;
//
//public class SheetBoardController {
//
//    @FXML
//    private GridPane sheetGrid;
//
//    @FXML
//    private Button updateButton;
//
//    @FXML
//    private TextField cellIdInput;
//
//    @FXML
//    private TextField newValueInput;
//
//    private SheetDTO currentSheet;
//
//    public void setSheet(SheetDTO sheet) {
//        this.currentSheet = sheet;
//        renderSheet();
//    }
//
//    private void renderSheet() {
//        sheetGrid.getChildren().clear(); // Clear previous UI elements
//
//        int numRows = currentSheet.getSheetSize().getNumRows();
//        int numCols = currentSheet.getSheetSize().getNumCols();
//
//        // Render row and column headers
//        for (int row = 0; row < numRows; row++) {
//            for (int col = 0; col < numCols; col++) {
//                CellDTO cellDTO = currentSheet.getCell(row, col);
//
//                // If the cellDTO exists, display its effective value
//                StringProperty cellValue = (cellDTO != null) ? cellDTO.effectiveValueProperty() : new SimpleStringProperty("");
//
//                TextField cellField = new TextField(cellValue.get());
//                cellField.setEditable(false);
//                sheetGrid.add(cellField, col + 1, row + 1); // +1 to account for headers
//            }
//        }
//    }
//
//    @FXML
//    private void updateCellValue() {
//        String cellId = cellIdInput.getText();
//        String newValue = newValueInput.getText();
//
//        // Parse cellId into row and column
//        int[] rowCol = parseCellId(cellId);
//        int row = rowCol[0];
//        int col = rowCol[1];
//
//        // Update the cell in the current sheet
//        if (currentSheet != null && row >= 0 && col >= 0) {
//            CellDTO cell = currentSheet.getCell(row, col);
//            if (cell != null) {
//                // Update the original value in the sheet
//                cell.effectiveValueProperty().set(newValue);
//                // Optionally, trigger recalculation if necessary
//            }
//        }
//
//        renderSheet(); // Re-render the grid to reflect changes
//    }
//
//    @FXML
//    private void handleResetStyles() {
//        // Implement reset styles functionality here
//    }
//
//    private int[] parseCellId(String cellId) {
//        try {
//            String column = cellId.replaceAll("\\d", ""); // Extract the letter(s) for the column
//            String row = cellId.replaceAll("\\D", ""); // Extract the number for the row
//
//            int rowIndex = Integer.parseInt(row) - 1; // Convert 1-based row to 0-based
//            int colIndex = column.charAt(0) - 'A'; // Convert column letter to 0-based index
//
//            return new int[]{rowIndex, colIndex};
//        } catch (Exception e) {
//            // Handle invalid cell ID format
//            return new int[]{-1, -1};
//        }
//    }
//}
