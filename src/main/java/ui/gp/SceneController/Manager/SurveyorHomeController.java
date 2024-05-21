package ui.gp.SceneController.Manager;

import com.sun.javafx.menu.MenuItemBase;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
import ui.gp.Models.Model;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.InsuranceSurveyor;
import ui.gp.Models.Users.Manager;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Controllers.ManagerController;
import ui.gp.SceneController.Controllers.SurveyorController;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.View.ViewFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurveyorHomeController {


    public Label welcomeBannerUser;
   
    public AnchorPane surveyorHomeScene;
    public Tab infoTab;
    public GridPane infoView;
    public TextField fullnameField;
    public TextField usernameField;
    public TextField passwordField;
    public TextField phonenumberField;
    public TextField emailField;
    public TextField addressField;
    public TextField idField;
    private DatabaseConnection databaseConnection;
    private ViewFactory view;
    private Customer selectedCustomer;
    private Claim selectedClaim;
    private InsuranceSurveyor surveyor;
    private SurveyorController surveyorController;

    @FXML
    Button managerViewCustomer;


    @FXML
    TableView <Claim> surveyorTable;
    @FXML
    TableColumn claimIDSurveyor;
    @FXML
    TableColumn insuredPersonSurveyor;
    @FXML
    TableColumn claimAmountSurveyor;
    @FXML
    TableColumn statusSurveyor;
    @FXML
    TableColumn claimDateSurveyor;
    @FXML
    TableColumn examDateSurveyor;

    @FXML
    TableView <Customer> customerSurveyorTable;
    @FXML
    TableColumn customerIDSurveyorView;
    @FXML
    TableColumn customerNameSurveyorView;
    @FXML
    TableColumn customerRoleSurveyorView;
    @FXML
    TableColumn customerPhoneSurveyorView;
    @FXML
    TableColumn customerEmailSurveyorView;
    @FXML
    TableColumn customerAddressSurveyorView;
    @FXML
    Button mangerViewCustomer;
    @FXML
    private Tab managerViewCustomerTab;


    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    public void SurveyorController(){
        this.databaseConnection = DatabaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

    public void initialize(InsuranceSurveyor surveyor, SurveyorController surveyorController) {
        bannerNameView(surveyor.getUsername());
        this.surveyor = surveyor;
        this.surveyorController = surveyorController;
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
        if (infoTab.isSelected()) {
            handleProfileTabSelection();
        }
        managerViewCustomerTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateCustomerSurveyorTable();
            }
        });

        customerSurveyorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedCustomer = (Customer) newSelection;
                managerViewCustomer.setDisable(false);
            } else {
                managerViewCustomer.setDisable(true);
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            populateCustomerSurveyorTable();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void handleProfileTabSelection() {
        if (infoTab.isSelected() && surveyorController != null) {
            String[] information = surveyorController.retrieveInformation().split("\n");
            idField.setText(information[0].split(": ")[1]);
            fullnameField.setText(information[1].split(": ")[1]);
            usernameField.setText(information[2].split(": ")[1]);
            passwordField.setText(information[3].split(": ")[1]);
            emailField.setText(information[4].split(": ")[1]);
            phonenumberField.setText(information[5].split(": ")[1]);
            addressField.setText(information[6].split(": ")[1]);
        }
    }

    @FXML
    public void SurveyorLogout(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(surveyorHomeScene);
        System.out.println("Insurance Surveyor logout");
    }

    @FXML
    public void onProfileSaveButton(ActionEvent event){

        String password = passwordField.getText();
        String email = emailField.getText();
        String phoneNumber = phonenumberField.getText();
        String address = addressField.getText();
        String username = usernameField.getText();

        //Save update data to the database
        updateProfile(password, email, phoneNumber, address, username);

    }

    @FXML
    public void onProfileResetButton(ActionEvent event){
        String[] information = surveyorController.retrieveInformation().split("\n");
        try {
            String query = "SELECT * FROM Users WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, idField.getText());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idField.setText(resultSet.getString("id"));
                fullnameField.setText(resultSet.getString("fullname"));
                usernameField.setText(resultSet.getString("username"));
                passwordField.setText(resultSet.getString("password"));
                emailField.setText(resultSet.getString("email"));
                phonenumberField.setText(resultSet.getString("phoneNumber"));
                addressField.setText(resultSet.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updateProfile(String password, String email, String phoneNumber, String address,String username)
    {
        try {
            String query = "UPDATE Users SET password = ?, email = ?, phonenumber = ?, address = ? WHERE username = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);

            statement.setString(1, password);
            statement.setString(2, email);
            statement.setString(3, phoneNumber);
            statement.setString(4, address);
            statement.setString(5, username);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateCustomerSurveyorTable(){
        List<Customer> customers = surveyorController.retrieveBeneficiaries();
        ObservableList<Customer> dataList = FXCollections.observableArrayList(customers);

        customerIDSurveyorView.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameSurveyorView.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        customerRoleSurveyorView.setCellValueFactory(new PropertyValueFactory<>("role"));
        customerPhoneSurveyorView.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        customerEmailSurveyorView.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerAddressSurveyorView.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerSurveyorTable.setItems(dataList);
        if (selectedCustomer != null){
            customerSurveyorTable.getSelectionModel().select((Customer) selectedCustomer);
        }
    }

    @FXML
    public void onShowDetail(ActionEvent event){
        if(selectedCustomer != null){
            customerSurveyorTable.getSelectionModel().clearSelection();
            managerViewCustomer.setDisable(true);
        } if(selectedCustomer.getRole().name().equals("Dependent")){
            view.showDependentInformation(selectedCustomer);
        } else if (selectedCustomer.getRole().name().equals("Policy_Holder")){
            view.showPolicyHolderInformation(selectedCustomer);
        } else if (selectedCustomer.getRole().name().equals("Insurance_Surveyor")){
            view.showPolicyHolderInformation(selectedCustomer);
        } else if (selectedCustomer.getRole().name().equals("Policy_Owner")){
            view.showPolicyHolderInformation(selectedCustomer);
        }
    }
}


