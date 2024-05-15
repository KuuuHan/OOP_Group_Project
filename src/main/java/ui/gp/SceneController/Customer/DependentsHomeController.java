package ui.gp.SceneController.Customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ui.gp.SceneController.SceneUtil;

import java.io.IOException;

public class DependentsHomeController {
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane dependentsHomeScene;

    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    public void dependentsLogout(ActionEvent logoutAction) throws IOException {
        System.out.println("Dependents logout");
        SceneUtil.logout(dependentsHomeScene);
    }
}

