package actionLine;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CellValueWindow {

    public void show(String value, String cellId) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cell Value");
        alert.setHeaderText("Cell ID: " + cellId);
        alert.setContentText("Value: " + value);
        alert.showAndWait();
    }
}
