package ui.gp.SceneController.Policy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import ui.gp.Models.Model;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.View.ViewFactory;

import java.io.IOException;

public class OwnerHomeController {
    public TextField idFieldInfo;
    public TextField fullnameFieldInfo;
    public TextField usernameFieldInfo;
    public TextField passwordFieldID;
    public TextField phonenumberFieldID;
    public TextField addressFieldID;
    public TextField emailFiieldID;
    public Button addPolicyHolderButton;
    public Button addDependentButton;
    public Button deleteBeneficiaryButton;
    public Button updateBeneficiaryButton;
    public Button showInfoBeneficiaryButton;
    public ComboBox filterBeneficiaryBox;
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane ownerHomeScene;
    @FXML
    Button logoutButton;
    ViewFactory view;

    public OwnerHomeController() {
       this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    @FXML
    public void logoutOwner(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(ownerHomeScene);
        System.out.println("Policy Owner logout");
    }

    public void AddPolicyHolderButton() throws IOException {
        view.showPolicyHolderForm();
    }

    public void AddDependentButton() throws IOException {
        view.showDependentForm();
    }
}
