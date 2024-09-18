package actionLine;

import fromEngine.CellDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.AppController;
import sheet.coordinate.Coordinate;

import java.util.List;

public class ActionLineController {

    @FXML private TextField cellIdTextField;
    @FXML private TextField lastUpdatedVersionTextField;
    @FXML private TextField originalValueTextField;
    @FXML private Button updateValueButton;
    @FXML private Button versionSelectorButton;
    private CellDTO selectedCell;
    private AppController mainController;

    @FXML
    void updateCellButtonActionListener(ActionEvent event) {
        if (selectedCell != null) {
            openUpdateValueDialog(selectedCell);
        } else {
            showErrorDialog("Error", "No cell selected", "Please select a cell to update.");
        }
    }

    @FXML
    void versionSelectorButtonActionListener(ActionEvent event) {
        openVersionSelectorDialog();
    }

    public void clearUIComponents() {
        cellIdTextField.clear();
        originalValueTextField.clear();
        lastUpdatedVersionTextField.clear();
        updateValueButton.setDisable(true);
        versionSelectorButton.setDisable(true);
    }

    public void updateFields(Coordinate coord, CellDTO cell) {
        cellIdTextField.setText(coord.toString());
        selectedCell = cell;
        updateValueButton.setDisable(false);
        if (cell == null) {
            originalValueTextField.setText("empty cell");
            lastUpdatedVersionTextField.setText("1");
        } else {
            originalValueTextField.setText(cell.getOriginalValue());
            lastUpdatedVersionTextField.setText(Integer.toString(cell.getVersion()));
        }
    }

    private void openUpdateValueDialog(CellDTO cell) {
        // Implement this method to open a dialog for updating the cell value
    }

    private void openVersionSelectorDialog() {
        // Implement this method to open the version selector dialog
    }

    private void showErrorDialog(String title, String header, String message) {
        // Implement this method to show an error dialog
        System.err.println(title + ": " + header + " - " + message);
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void enableVersionSelector() {
        versionSelectorButton.setDisable(false);
    }
}
