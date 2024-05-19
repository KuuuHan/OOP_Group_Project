package ui.gp.Tab;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;




import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    private Button nextButton;

    @FXML
    private Button prevButton;

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
        // Assuming 'files' is the list of selected files
        List<String> pdfFileNames = new ArrayList<>();
        for (File file : selectedFiles) {
            if (!file.canWrite()) {
                System.out.println("File is read-only: " + file.getName());
                continue;
            }
            String fileName = file.getName();
            String newFileName = convertToPdf(fileName);
            File newFile = new File(file.getParent(), newFileName);
            if (newFile.exists()) {
                System.out.println("File with the same name already exists: " + newFileName);
                continue;
            }
            try {
                Files.move(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                claimController.updateFileNameComboBox(newFileName); // update the ComboBox in ClaimController
            } catch (IOException e) {
                System.out.println("Failed to rename file: " + fileName);
                e.printStackTrace();
            }
        }
    }

    public String convertToPdf(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            // If there is an extension, remove it and replace with .pdf
            return fileName.substring(0, dotIndex) + ".pdf";
        } else {
            // If there is no extension, just append .pdf
            return fileName + ".pdf";
        }
    }
}
