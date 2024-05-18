package ui.gp.SceneController.Policy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import ui.gp.Models.Claim;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.Tab.ClaimController;
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
    @FXML
    TableView<Claim> TaView; // replace Claim with the actual type of your table rows
    @FXML
    Button deleteClaimPoilicyOwnerButton;
    @FXML
    Button updateClaimPoilicyOwnerButton;
    public ComboBox filterBeneficiaryBox;
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane ownerHomeScene;
    @FXML
    Button logoutButton;
    ViewFactory view = new ViewFactory();
    ClaimController claimController = new ClaimController();


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
    public void initialize() {
        // Create a new Claim
        //chiu chua biet data luu kieu deo gi
        // cant blur update and delete yet
    }
    @FXML
    Button addItemButton;

    @FXML
    public void addItemOnClick(ActionEvent event) throws IOException {
        claimController.addItemOnClick();
    }

}
