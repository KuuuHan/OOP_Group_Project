package ui.gp.SceneController.Customer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DependentsHomeController {
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane dependentsHomeScene;

    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    public void dependentsLogout() {
        System.out.println("Dependents logout");
    }
}

