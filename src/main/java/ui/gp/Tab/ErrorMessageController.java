package ui.gp.Tab;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorMessageController {

    @FXML
    private Label errorMessageLabel;

    public void setErrorMessage(String errorMessage) {
        errorMessageLabel.setText(errorMessage);
    }

    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) errorMessageLabel.getScene().getWindow();
        stage.close();
    }
}
