package header;

import jakarta.xml.bind.JAXBException;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import files.loader.SheetFactory;
import fromEngine.SheetDTO;
import main.AppController;
import sheet.Sheet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class HeaderController {

    @FXML private TextField fileNameTA;
    @FXML private TextField filePathTA;
    @FXML private Button loadFileButton;
    private AppController mainController;
    private Consumer<Void> sheetLoadedListener;
    private String xmlFilePath;

    private SheetDTO loadedSheetDTO; // Store the loaded SheetDTO

    @FXML
    void loadFileButtonActionListener(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            xmlFilePath = selectedFile.getAbsolutePath();
            filePathTA.setText(selectedFile.getAbsolutePath());
            fileNameTA.setText("Loading...");

            showProgressBarPopup(selectedFile);
        }
    }

    private void showProgressBarPopup(File file) {
        Stage progressBarStage = new Stage();
        progressBarStage.initModality(Modality.APPLICATION_MODAL);
        progressBarStage.setTitle("Loading File");

        ProgressBar fileProgressBar = new ProgressBar();
        Label progressLabel = new Label("Loading, please wait...");

        VBox vbox = new VBox(10, progressLabel, fileProgressBar);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 300, 120);
        progressBarStage.setScene(scene);
        progressBarStage.show();

        loadFile(file, fileProgressBar, progressBarStage);
    }

    private void loadFile(File file, ProgressBar fileProgressBar, Stage progressBarStage) {
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() {
                try {
                    updateProgress(0, 1);
                    for (int i = 0; i <= 10; i++) {
                        Thread.sleep(200); // Simulate work
                        updateProgress(i / 10.0, 1);
                    }

                    // Load the sheet and convert to SheetDTO
                    Sheet sheet = SheetFactory.loadFile(file.getAbsolutePath());
                    loadedSheetDTO = SheetDTO.createSheetDTO(sheet); // Convert to SheetDTO

                    updateProgress(1, 1);
                    Thread.sleep(400); // Simulate pause

                    javafx.application.Platform.runLater(() -> fileNameTA.setText(file.getName()));
                } catch (IOException | JAXBException e) {
                    updateMessage("Failed to load file: " + e.getMessage());
                    e.printStackTrace();
                    javafx.application.Platform.runLater(() -> showErrorDialog("File Load Error", "Failed to load file:", e));
                } catch (InterruptedException e) {
                    updateMessage("Loading interrupted.");
                    e.printStackTrace();
                    javafx.application.Platform.runLater(() -> showErrorDialog("Loading Interrupted", "The loading process was interrupted.", e));
                }
                return null;
            }
        };

        fileProgressBar.progressProperty().bind(loadTask.progressProperty());

        loadTask.setOnSucceeded(e -> {
            javafx.application.Platform.runLater(() -> fileNameTA.setText(file.getName()));
            progressBarStage.close();
            passSheetDTOToNextController(); // Pass the loaded SheetDTO to the next controller
        });

        loadTask.setOnFailed(e -> javafx.application.Platform.runLater(() -> {
            fileNameTA.setText("Error loading file.");
            progressBarStage.close();
            Throwable ex = loadTask.getException();
            if (ex != null) {
                showErrorDialog("Unexpected Error", "An unexpected error occurred:", ex);
            }
        }));

        CompletableFuture.runAsync(loadTask);
    }

    private void showErrorDialog(String title, String header, Throwable exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(exception.getMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stack trace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(textArea, Priority.ALWAYS);

        VBox expContent = new VBox();
        expContent.getChildren().addAll(label, textArea);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    private void passSheetDTOToNextController() {
        if (loadedSheetDTO != null && mainController != null) {
            // Pass the SheetDTO to the AppController
            mainController.showSheet(loadedSheetDTO);

            // Notify that the sheet is loaded
            if (sheetLoadedListener != null) {
                sheetLoadedListener.accept(null);
            }
        }
    }

    @FXML
    public void initialize() {
        if (fileNameTA != null) {
            fileNameTA.setEditable(false);
        }
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setSheetLoadedListener(Consumer<Void> listener) {
        this.sheetLoadedListener = listener;
    }

    public String getXmlFilePath() {
        return xmlFilePath;
    }
}