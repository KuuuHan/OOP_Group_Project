package ui.gp.SceneController.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import ui.gp.SceneController.Function.SceneUtil;

import java.io.IOException;

public class AdminHomeController {
    @FXML
    AnchorPane adminHomeScene;
    public void adminLogout(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(adminHomeScene);
        System.out.println("System Admin logout");
    }
}
