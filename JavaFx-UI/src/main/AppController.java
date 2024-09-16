package main;

import actionLine.ActionLineController;
import fromEngine.SheetDTO;
import header.HeaderController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import sheetBoard.ControllerSheet;

public class AppController extends Application {

    @FXML private BorderPane headerComponent;
    @FXML private ScrollPane actionLineComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private ActionLineController actionLineComponentIncludeController;
    @FXML private ControllerSheet sheetComponentIncludeController;

    @FXML
    public void initialize() {
        if (headerComponentController != null && actionLineComponentIncludeController != null) {
            headerComponentController.setMainController(this);
            actionLineComponentIncludeController.setMainController(this);
            sheetComponentIncludeController.setMainController(this);
            headerComponentController.setSheetLoadedListener(event -> actionLineComponentIncludeController.enableVersionSelector());
        }
    }

    public void showSheet(SheetDTO sheet) {
        if (sheet == null) {
            showErrorDialog("Sheet Error", "Sheet Loading Error", "The sheet could not be loaded.");
            return;
        }

        actionLineComponentIncludeController.clearUIComponents();
        sheetComponentIncludeController.clearGrid();
        sheetComponentIncludeController.setSheetDTO(sheet);
        sheetComponentIncludeController.createGridFromSheetDTO();
    }

    public void showErrorDialog(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("app.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            // Add this line to link the CSS
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sheet Cell Application");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Application Error", "Failed to Load", "An error occurred while loading the application.");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    public HeaderController getHeaderController() {
        return headerComponentController;
    }

    public ActionLineController getActionLineController() {
        return actionLineComponentIncludeController;
    }
}
