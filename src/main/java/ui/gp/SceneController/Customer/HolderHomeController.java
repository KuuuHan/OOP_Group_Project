package ui.gp.SceneController.Customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ui.gp.SceneController.SceneUtil;

import java.io.IOException;

public class HolderHomeController {
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane policyHolderHomeScene;


    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    @FXML
    public void logoutCustomer(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(policyHolderHomeScene);
    }
}
