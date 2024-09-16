package actionLine;

import fromEngine.CellDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class UpdateValueController {

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField newValueField; // TextField to enter the new value

    private final CellDTO cell;
    private final List<String> allCellNames;
    private Stage stage; // Reference to the Stage
    private boolean confirmed = false; // To check if the action was confirmed
    private String generatedString; // To store the generated value

    public UpdateValueController(CellDTO cell, List<String> allCellNames, Stage stage) {
        this.cell = cell;
        this.allCellNames = allCellNames;
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        // Initialize any default values or settings here
    }

    @FXML
    private void handleConfirmButtonAction() {
        // Logic to handle confirmation
        generatedString = newValueField.getText(); // Retrieve the new value
        confirmed = true; // Set confirmed to true
        closeWindow();
    }

//    @FXML
//    private void handleCancelButtonAction() {
//        // Logic to handle cancellation
//        confirmed = false; // Set confirmed to false
//        closeWindow();
//    }

    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }

    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/update_value_dialog.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            if (stage == null) {
                stage = new Stage();
            }
            stage.setScene(new Scene(root));
            stage.setTitle("Update Cell Value");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions properly in real scenarios
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getGeneratedString() {
        return generatedString;
    }
}
