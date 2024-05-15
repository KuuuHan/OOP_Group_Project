package ui.gp.SceneController.Customer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CustomerHomeController {
    @FXML
    Label welcomeBannerUser;

    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }
}
