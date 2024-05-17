package ui.gp.SceneController.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ui.gp.SceneController.Function.SceneUtil;

import java.io.IOException;

public class ManagerHomeController {
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane managerHomeScene;


    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    @FXML
    public void logoutOwner(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(managerHomeScene);
    }
}