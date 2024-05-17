package ui.gp.SceneController.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ui.gp.SceneController.Function.SceneUtil;

import java.io.IOException;


public class SurveyorHomeController {
    @FXML
    public Label welcomeBannerUser;
    @FXML
    AnchorPane surveyorHomeScene;
    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }
    @FXML
    public void surveyorLogout(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(surveyorHomeScene);
        System.out.println("Insurance Surveyor logout");
    }
}
