package ui.gp.SceneController.Policy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import ui.gp.Models.Model;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.Dependent;
import ui.gp.Models.Users.PolicyOwner;
import ui.gp.SceneController.Controllers.DependentController;
import ui.gp.SceneController.Controllers.PolicyOwnerController;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.Tab.ClaimController;
import ui.gp.View.ViewFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OwnerHomeController {
    public TableColumn idPolicyOwnerTable;
    public TableColumn fullnamePolicyOwnerTable;
    public TableColumn usernamePoilicyOwnerTable;
    public TableColumn emailPoilicyOwnerTable;
    public TableColumn phonenumberPoilicyOwnerTable;
    public TableColumn passwordPoilicyOwnerTable;
    public TableColumn addressPoilicyOwnerTable;
    public Tab BeneficiaryTab;
    public TableView policyOwnerTable;
    public TableColumn rolePoilicyOwnerTable;
    private  PolicyOwner policyOwner;
    private PolicyOwnerController policyOwnerController;
    public TextField idFieldInfo;
    public TextField fullnameFieldInfo;
    public TextField usernameFieldInfo;
    public TextField passwordFieldInfo;
    public TextField phonenumberFieldInfo;
    public TextField addressFieldInfo;
    public TextField emailFiieldInfo;
    public Button addPolicyHolderButton;
    public Button addDependentButton;
    public Button deleteBeneficiaryButton;
    public Button updateBeneficiaryButton;
    public Button showInfoBeneficiaryButton;
    public ComboBox filterBeneficiaryBox;
    public Tab infoTab;
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane ownerHomeScene;
    @FXML
    Button logoutButton;
    ViewFactory view;

    public void initialize(PolicyOwner policyOwner, PolicyOwnerController policyOwnerController) {
        this.policyOwner = policyOwner;
        this.policyOwnerController = policyOwnerController;
        if (infoTab.isSelected()) {
            handleProfileTabSelection();
        }
        BeneficiaryTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populatePolicyOwnerTable();
            }
        });
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            populatePolicyOwnerTable();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

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


    @FXML
    public void handleProfileTabSelection() {
        if (infoTab.isSelected() && policyOwnerController != null) {
            String[] information = policyOwnerController.retrieveInformation().split("\n");
            idFieldInfo.setText(information[0].split(": ")[1]);
            fullnameFieldInfo.setText(information[1].split(": ")[1]);
            usernameFieldInfo.setText(information[2].split(": ")[1]);
            passwordFieldInfo.setText(information[3].split(": ")[1]);
            emailFiieldInfo.setText(information[4].split(": ")[1]);
            phonenumberFieldInfo.setText(information[5].split(": ")[1]);
            addressFieldInfo.setText(information[6].split(": ")[1]);
        }
    }

    public void populatePolicyOwnerTable() {

        List<Customer> beneficiaries = policyOwnerController.retrieveBeneficiaries();
        ObservableList<Customer> data = FXCollections.observableArrayList(beneficiaries);

        idPolicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullnamePolicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        usernamePoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("email"));
        phonenumberPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        passwordPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("password"));
        addressPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("address"));
        rolePoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("role"));

        policyOwnerTable.setItems(data);
    }
    @FXML
    public void addItemOnClick(ActionEvent event) throws IOException {
        ClaimController claimController = new ClaimController();
        claimController.addItemOnClick();
    }

}
