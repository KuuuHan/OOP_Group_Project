package ui.gp.SceneController.Customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ui.gp.SceneController.SceneUtil;

import java.io.IOException;

public class OwnerHomeController {
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane ownerHomeScene;


    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    @FXML
    public void logoutOwner(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(ownerHomeScene);
    }
}