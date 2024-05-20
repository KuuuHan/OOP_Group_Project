package ui.gp.SceneController.Policy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TablePositionBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.PolicyHolder;
import ui.gp.Models.Users.PolicyOwner;
import ui.gp.SceneController.Controllers.PolicyHolderController;
import ui.gp.SceneController.Controllers.PolicyOwnerController;
import ui.gp.SceneController.Function.SceneUtil;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HolderHomeController {
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane policyHolderHomeScene;
    @FXML
    Tab infoTab;
    @FXML
    TextField idFieldInfo;
    @FXML
    TextField fullnameFieldInfo;
    @FXML
    TextField usernameFieldInfo;
    @FXML
    TextField passwordFieldInfo;
    @FXML
    TextField emailFieldInfo;
    @FXML
    TextField phonenumberFieldInfo;
    @FXML
    TextField addressFieldInfo;

    private PolicyHolder policyHolder;
    private PolicyHolderController policyHolderController;



    public void initialize(PolicyHolder policyHolder, PolicyHolderController policyHolderController) {
        bannerNameView(policyHolder.getFullname());
        this.policyHolder = policyHolder;
        this.policyHolderController = policyHolderController;
        if (infoTab.isSelected()) {
            handleProfileTabSelection();
        }
//        BeneficiaryTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                populatePolicyOwnerTable();
//                deleteBeneficiaryButton.setDisable(true);
//                showInfoBeneficiaryButton.setDisable(true);
//                updateBeneficiaryButton.setDisable(true);
//            }
//        });

//        policyOwnerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                selectedBeneficiary = (Customer) newSelection;
//                deleteBeneficiaryButton.setDisable(false);
//                showInfoBeneficiaryButton.setDisable(false);
//                updateBeneficiaryButton.setDisable(false);
//            } else {
//                deleteBeneficiaryButton.setDisable(true);
//                showInfoBeneficiaryButton.setDisable(true);
//                updateBeneficiaryButton.setDisable(true);
//            }
//        });
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
//            populatePolicyOwnerTable();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

//        List<String> filterList = new ArrayList<>();
//        filterList.add("All");
//        filterList.add("Policy Holder");
//        filterList.add("Dependent");
//        filterBeneficiaryBox.setItems(FXCollections.observableArrayList(filterList));
//        filterBeneficiaryBox.setValue(filterList.get(0));

    }


    public void handleProfileTabSelection() {
        if (infoTab.isSelected() && policyHolderController != null) {
            String[] information = policyHolderController.retrieveInformation().split("\n");
            idFieldInfo.setText(information[0].split(": ")[1]);
            fullnameFieldInfo.setText(information[1].split(": ")[1]);
            usernameFieldInfo.setText(information[2].split(": ")[1]);
            passwordFieldInfo.setText(information[3].split(": ")[1]);
            emailFieldInfo.setText(information[4].split(": ")[1]);
            phonenumberFieldInfo.setText(information[5].split(": ")[1]);
            addressFieldInfo.setText(information[6].split(": ")[1]);
        }
    }


    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    @FXML
    public void logoutOwner(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(policyHolderHomeScene);
        System.out.println("Policy Holder logout");
    }

    @FXML
    public void onProfileSaveButton(ActionEvent event){
        String password = passwordFieldInfo.getText();
        String email = emailFieldInfo.getText();
        String phoneNumber = phonenumberFieldInfo.getText();
        String address = addressFieldInfo.getText();
        String username = usernameFieldInfo.getText();

        //Save update data to the database
        updateProfile(password, email, phoneNumber, address, username);

    }

    @FXML
    public void onProfileResetButton(ActionEvent event){
        String[] information = policyHolderController.retrieveInformation().split("\n");
        try {
            String query = "SELECT * FROM Users WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, idFieldInfo.getText());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idFieldInfo.setText(resultSet.getString("id"));
                fullnameFieldInfo.setText(resultSet.getString("fullname"));
                usernameFieldInfo.setText(resultSet.getString("username"));
                passwordFieldInfo.setText(resultSet.getString("password"));
                emailFieldInfo.setText(resultSet.getString("email"));
                phonenumberFieldInfo.setText(resultSet.getString("phoneNumber"));
                addressFieldInfo.setText(resultSet.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updateProfile(String password, String email, String phoneNumber, String address,String username)
    {
        try {
            String query = "UPDATE Users SET password = ?, email = ?, phoneNumber = ?, address = ? WHERE username = ?";
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
}
