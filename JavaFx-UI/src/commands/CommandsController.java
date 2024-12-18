//package commands;
//
//import javafx.application.Platform;
//import javafx.fxml.FXML;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.ColorPicker;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import main.AppController;
//import sheetBoard.ControllerSheet;
//import javafx.scene.paint.Color;
//import sheet.coordinate.Coordinate;
//
//public class CommandsController {
//
//    private AppController mainController;
//
//    @FXML private ComboBox<String> themeComboBox;
//    @FXML private Label selectedCellLabel;
//    @FXML private ColorPicker backgroundColorPicker;
//    @FXML private ColorPicker textColorPicker;
//    @FXML private Button applyStylesButton;
//    @FXML private Button resetStylesButton;
//    @FXML private Label selectedColumnLabel;
//    @FXML private Button leftAlignment;
//    @FXML private Button rightAlignment;
//    @FXML private Button centerAlignment;
//    private Coordinate selectedCellCoordinate;
//    private Pos selectedAlignment = Pos.CENTER;
//    private int selectedColumnIndex;
//
//    @FXML
//    private void initialize() {
//        themeComboBox.getItems().addAll("Basic", "Pink", "Blue", "Green");
//        themeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                applyTheme(newValue);
//            }
//        });
//        backgroundColorPicker.setValue(Color.WHITE);
//        textColorPicker.setValue(Color.BLACK);
//        setEditCellDisable(true);
//        setAligmentButtonsDisable(true);
//
//        leftAlignment.setOnAction(event -> handleLeftAlignmentAction());
//        centerAlignment.setOnAction(event -> handleCenterAlignmentAction());
//        rightAlignment.setOnAction(event -> handleRightAlignmentAction());
//    }
//
//    public void setAligmentButtonsDisable(boolean disable)
//    {
//        rightAlignment.setDisable(disable);
//        leftAlignment.setDisable(disable);
//        centerAlignment.setDisable(disable);
//    }
//    @FXML
//    private void handleLeftAlignmentAction() {
//        selectedAlignment = Pos.CENTER_LEFT;
//        applyColumnAlignment();
//    }
//
//    @FXML
//    private void handleCenterAlignmentAction() {
//        selectedAlignment = Pos.CENTER;
//        applyColumnAlignment();
//    }
//
//    @FXML
//    private void handleRightAlignmentAction() {
//        selectedAlignment = Pos.CENTER_RIGHT;
//        applyColumnAlignment();
//    }
//
//    public Pos getSelectedAlignment() {
//        return selectedAlignment;
//    }
//
//    private void applyColumnAlignment() {
//        if (selectedColumnIndex > 0) { // Ensure a column is selected
//            ControllerSheet sheetController = mainController.getSheetComponentController();
//            sheetController.setColumnAlignment(selectedColumnIndex, selectedAlignment);
//            setAligmentButtonsDisable(true);
//        }
//    }
//    public void setEditCellDisable(boolean disable) {
//        backgroundColorPicker.setDisable(disable);
//        textColorPicker.setDisable(disable);
//        applyStylesButton.setDisable(disable);
//        resetStylesButton.setDisable(disable);
//    }
//
//    public void updateCellCoordinate(Coordinate coordinate) {
//        selectedCellLabel.setText(coordinate.toString());
//        this.selectedCellCoordinate = coordinate;
//    }
//
//    public void updateColumn(int column) {
//        String colHeader = String.valueOf((char) ('A' + column-1));
//        selectedColumnIndex = column;
//        selectedColumnLabel.setText(colHeader);
//    }
//
//    @FXML
//    private void handleApplyStylesButtonHandle() {
//        if (selectedCellCoordinate != null) {
//            ControllerSheet sheetController = mainController.getSheetComponentController();
//            Color backgroundColor = backgroundColorPicker.getValue();
//            Color textColor = textColorPicker.getValue();
//            sheetController.updateCellStyle(selectedCellCoordinate, backgroundColor, textColor);
//            backgroundColorPicker.setValue(Color.WHITE);
//            textColorPicker.setValue(Color.BLACK);
//        }
//    }
//
//    @FXML
//    private void handleResetStylesButtonHandle() {
//        if (selectedCellCoordinate != null) {
//            ControllerSheet sheetController = mainController.getSheetComponentController();
//            sheetController.resetCellStyle(selectedCellCoordinate);
//            backgroundColorPicker.setValue(Color.WHITE);
//            textColorPicker.setValue(Color.BLACK);
//        }
//    }
//
//    public void setMainController(AppController mainController) {
//        this.mainController = mainController;
//    }
//    private void applyTheme(String themeName) {
//        if (mainController != null) {
//            Platform.runLater(() -> {
//                //mainController.setSheetStyle(themeName);
//                ControllerSheet sheetController = mainController.getSheetComponentController();
//                if (sheetController != null) {
//                    //sheetController.setSheetStyle(themeName);
//                }
//            });
//        } else {
//            System.err.println("MainController is not set. Cannot apply theme.");
//        }
//    }
//
//}