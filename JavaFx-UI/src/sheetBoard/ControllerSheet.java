package sheetBoard;

import fromEngine.CellDTO;
import fromEngine.SheetDTO;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import main.AppController;
import sheet.coordinate.Coordinate;
import sheet.coordinate.CoordinateFactory;

public class ControllerSheet {

    @FXML
    private GridPane gridPane;
    private SheetDTO sheetDTO;
    private boolean readOnly = false;
    private AppController mainController;

    @FXML
    public void initialize() {
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));
    }

    public void setSheetDTO(SheetDTO sheetDTO) {
        this.sheetDTO = sheetDTO;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void createGridFromSheetDTO() {
        gridPane.getChildren().clear();
        int rows = sheetDTO.getSheetSize().getNumRows();
        int columns = sheetDTO.getSheetSize().getNumCols();

        initializeColumnConstraints(columns);
        initializeRowConstraints(rows);

        // Create column headers
        for (int col = 0; col < columns; col++) {
            String colHeader = String.valueOf((char) ('A' + col));
            Label colLabel = createCellLabel(colHeader, "header", null);
            gridPane.add(colLabel, col + 1, 0);
        }

        // Create row headers
        for (int row = 0; row < rows; row++) {
            String rowHeader = String.valueOf(row + 1);
            Label rowLabel = createCellLabel(rowHeader, "header", null);
            gridPane.add(rowLabel, 0, row + 1);
        }

        // Create cell labels
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Coordinate coordinate = CoordinateFactory.createCoordinate(row, col);
                CellDTO cellDTO = sheetDTO.getCell(coordinate.getRow(), coordinate.getColumn());
                String cellValue = cellDTO != null && cellDTO.getEffectiveValue() != null
                        ? cellDTO.getEffectiveValue()
                        : "";

                String styleClass = readOnly ? "read-only-cell" : "editable-cell";
                Label cellLabel = createCellLabel(cellValue, styleClass, coordinate);

                gridPane.add(cellLabel, col + 1, row + 1);
            }
        }

        if (readOnly) {
            makeGridReadOnly();
        }
    }

    private void initializeColumnConstraints(int columns) {
        gridPane.getColumnConstraints().clear();
        for (int i = 0; i < columns + 1; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setMinWidth(50);
            column.setMaxWidth(Double.MAX_VALUE);
            column.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(column);
        }
    }

    private void initializeRowConstraints(int rows) {
        gridPane.getRowConstraints().clear();
        for (int i = 0; i < rows + 1; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(30);
            row.setMaxHeight(Double.MAX_VALUE);
            row.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(row);
        }
    }

    private Label createCellLabel(String text, String styleClass, Coordinate coordinate) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        label.setPrefSize(100, 30);
        label.setMinSize(50, 30);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        if (!readOnly && coordinate != null) {
            label.setOnMouseClicked(event -> handleCellClick(event, coordinate));
        }

        return label;
    }

    private void handleCellClick(MouseEvent event, Coordinate coordinate) {
        // Handle cell click logic here
        // For example, you can notify the main controller or update the action line
        if (mainController != null) {
            CellDTO cellDTO = sheetDTO.getCell(coordinate.getRow(), coordinate.getColumn());
            mainController.getActionLineController().updateFields(coordinate, cellDTO);
        }
    }

    public void clearGrid() {
        gridPane.getChildren().clear();
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    private void makeGridReadOnly() {
        for (var node : gridPane.getChildren()) {
            if (node instanceof Label label) {
                label.setDisable(true);
            }
        }
    }
}
