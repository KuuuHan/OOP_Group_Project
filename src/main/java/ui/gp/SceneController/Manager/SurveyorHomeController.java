package ui.gp.SceneController.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.Tab.ClaimController;

import java.io.IOException;


public class SurveyorHomeController {
    @FXML
    public Label welcomeBannerUser;
    @FXML
    AnchorPane surveyorHomeScene;
    @FXML
    ClaimController claimController;
    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }
    @FXML
    public void surveyorLogout(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(surveyorHomeScene);
        System.out.println("Insurance Surveyor logout");
    }
    public void processClaim(ActionEvent event) throws IOException {
        claimController.processClaim(event);
    }
}
