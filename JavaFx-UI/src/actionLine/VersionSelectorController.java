package actionLine;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class VersionSelectorController {

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label versionLabel;

    private final int currentVersion;
    private Stage stage; // Reference to the Stage

    public VersionSelectorController(int currentVersion, Stage stage) {
        this.currentVersion = currentVersion;
        this.stage = stage;
    }

    @FXML
    private void handleConfirmButtonAction() {
        // Your logic for confirm button action
        closeWindow();
    }

    @FXML
    private void handleCancelButtonAction() {
        // Your logic for cancel button action
        closeWindow();
    }

    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }

    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/version_selector_dialog.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            if (stage == null) {
                stage = new Stage();
            }
            stage.setScene(new Scene(root));
            stage.setTitle("Select Version");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions properly in real scenarios
        }
    }
}
