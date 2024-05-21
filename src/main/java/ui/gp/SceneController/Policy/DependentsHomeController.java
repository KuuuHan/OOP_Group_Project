package ui.gp.SceneController.Policy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
import ui.gp.Models.Model;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.Dependent;
import ui.gp.SceneController.Controllers.DependentController;
import ui.gp.SceneController.Function.LoadingSceneController;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.View.ViewFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DependentsHomeController {
    private Dependent dependent;
    private DependentController dependentController;

    @FXML
    private BorderPane ViewClaimPane;

    @FXML
    private TextField addressField;

    @FXML
    private TableColumn dependentClaimAmount;

    @FXML
    private TableColumn dependentClaimDate;

    @FXML
    private ComboBox<String> dependentClaimFIlter;

    @FXML
    private TableColumn dependentClaimId;

    @FXML
    private TableColumn dependentClaimStatus;

    @FXML
    private TableColumn dependentExamDate;

    @FXML
    private TableColumn dependentInsurancenNumber;

    @FXML
    private TextField dependentSearchClaim;

    @FXML
    private AnchorPane dependentsHomeScene;

    @FXML
    private TabPane dependentsTabPane;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField idField11;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private Tab profileTab;

    @FXML
    private TextField usernameField;

    @FXML
    private Label welcomeBannerUser;
    @FXML
    private TableView dependentClaimTable;
    @FXML
    private Button dependentViewClaim;
    @FXML
    private Tab dependentClaimTab;
    @FXML
    private Tab dependentInfoTab;
    private Claim selectedClaim;
    private ViewFactory view;
    private DatabaseConnection databaseConnection;

    public DependentsHomeController() {
        this.databaseConnection = DatabaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

    public void initialize(Dependent dependent, DependentController dependentController) {
        this.dependent = dependent;
        this.dependentController = dependentController;


        if (dependentInfoTab.isSelected()) {
            handleProfileTabSelection();
        }

        dependentClaimTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateDependentClaimTable();
            }
        });

        dependentClaimTable.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
            if (newSelection != null)
            {
                selectedClaim = (Claim) newSelection;
                dependentViewClaim.setDisable(false);
            } else{
                dependentViewClaim.setDisable(true);
            }
        });



        List<String> displayClaimComboList = new ArrayList<>();
        displayClaimComboList.add("All");
        displayClaimComboList.add("Rejected");
        displayClaimComboList.add("Approved");
        displayClaimComboList.add("Pending");
        displayClaimComboList.add("NextStage");
        dependentClaimFIlter.setItems(FXCollections.observableArrayList(displayClaimComboList));
        dependentClaimFIlter.setValue(displayClaimComboList.get(0));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
//            populatePolicyOwnerTable();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    public void dependentsLogout(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(dependentsHomeScene);
        System.out.println("Dependents logout");
    }

    public void loadingTest(ActionEvent loadingTestEvent) throws IOException { //Mo phong server dang chay
        LoadingSceneController loadingSceneController = new LoadingSceneController();
        loadingSceneController.serverRespondingHold();
    }


    @FXML
    public void handleProfileTabSelection() {
        if (dependentInfoTab.isSelected() && dependentController != null) {
            String[] information = dependentController.retrieveInformation().split("\n");
            idField.setText(information[0].split(": ")[1]);
            fullNameField.setText(information[1].split(": ")[1]);
            usernameField.setText(information[2].split(": ")[1]);
            passwordField.setText(information[3].split(": ")[1]);
            emailField.setText(information[4].split(": ")[1]);
            phoneField.setText(information[5].split(": ")[1]);
            addressField.setText(information[6].split(": ")[1]);
        }
    }

    public void populateDependentClaimTable() {
        List<Claim> Claim = dependentController.retrieveClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(Claim);

        dependentClaimId.setCellValueFactory(new PropertyValueFactory<>("id"));
        dependentClaimDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        dependentExamDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        dependentClaimStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        dependentClaimAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        dependentInsurancenNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);

        dependentSearchClaim.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (claim.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });
        dependentClaimTable.setItems(filteredData);
        if (selectedClaim != null)
        {
            dependentClaimTable.getSelectionModel().select(selectedClaim);
        }
    }

    public void showClaimButtonAction() {
        if (selectedClaim != null) {
            dependentClaimTable.getSelectionModel().clearSelection();
            dependentViewClaim.setDisable(true);
            view.showSpecificClaimForm(selectedClaim);
        }


    }

    public void onStatusFilterBox(ActionEvent event) {
        String filter = dependentClaimFIlter.getSelectionModel().getSelectedItem();
        if (filter != null) {
            if (filter.equals("All")) {
                dependentClaimTable.setItems(FXCollections.observableArrayList(dependentController.retrieveClaims()));
            } else {
                ObservableList<Claim> filteredData = FXCollections.observableArrayList();
                for (Claim claim : dependentController.retrieveClaims()) {
                    if (claim.getStatus().equals(filter.replace(" ", "_"))) {
                        filteredData.add(claim);
                    }
                }
                dependentClaimTable.setItems(filteredData);
            }
        }
    }
}

