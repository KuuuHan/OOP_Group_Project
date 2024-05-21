package ui.gp.Tab;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UploadController {
    //when you create an instance of UploadController, make sure to call setClaimController:
//UploadController uploadController = new UploadController();
//uploadController.setClaimController(claimControllerInstance);
    private ClaimController claimController;

    public void setClaimController(ClaimController claimController) {
        this.claimController = claimController;
    }

    @FXML
    private Button uploadButton;

    @FXML
    private ImageView imageView;
    @FXML
    private Button ConfirmImage;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    Connection connection;
    File file;
    FileInputStream fis;
    String query;
    PreparedStatement ps;

    private List<File> selectedFiles;
    private int currentImageIndex = 0;
    public static List<String> selectedFilez = new ArrayList<>();
    @FXML
    private ComboBox<String> fileNameComboBox;

    // This method is called after the file name has been changed
    public void updateComboBox(String newFileName) {
        fileNameComboBox.getItems().add(newFileName);
    }

    @FXML
    public void chooseFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            currentImageIndex = 0;
            displayImage(selectedFiles.get(currentImageIndex));
        }
    }

    @FXML
    public void showNextImage() {
        if (selectedFiles != null && !selectedFiles.isEmpty() && currentImageIndex < selectedFiles.size() - 1) {
            currentImageIndex++;
            displayImage(selectedFiles.get(currentImageIndex));
        }
    }

    public List<File> getSelectedFiles() {
        return selectedFiles;
    }

    @FXML
    public void showPrevImage() {
        if (selectedFiles != null && !selectedFiles.isEmpty() && currentImageIndex > 0) {
            currentImageIndex--;
            displayImage(selectedFiles.get(currentImageIndex));
        }
    }

    private void displayImage(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            Image image = new Image(fis);
            imageView.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onConfirm() {
        List<File> updatedFiles = new ArrayList<>();
        for (File file : selectedFiles) {
            File newFile = new File(file.getParent(), file.getName());
            try {
                Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                updatedFiles.add(newFile); // Add the new file to the updatedFiles list
            } catch (IOException e) {
                System.out.println("Failed to copy file: " + file.getName());
                e.printStackTrace();
            }
        }
        selectedFiles = updatedFiles; // Update the selectedFiles list to reflect the new file names
        Stage stage = (Stage) ConfirmImage.getScene().getWindow();
        stage.close();
    }
}