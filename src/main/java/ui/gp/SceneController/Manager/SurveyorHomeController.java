package ui.gp.SceneController.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ui.gp.Database.DatabaseConnection;
import ui.gp.SceneController.Controllers.ManagerController;
import ui.gp.SceneController.Controllers.SurveyorController;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.Tab.ClaimController;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SurveyorHomeController {
    @FXML
    public Label welcomeBannerUser;
    @FXML
    AnchorPane surveyorHomeScene;
    @FXML
    ClaimController claimController;
    @FXML
    private Tab infoTab;
    @FXML
    private Tab AccountTab;
    @FXML
    private TextField idFieldInfo;
    @FXML
    private TextField fullnameFieldInfo;
    @FXML
    private TextField usernameFieldInfo;
    @FXML
    private TextField passwordFieldInfo;
    @FXML
    private TextField emailFieldInfo;
    @FXML
    private TextField phonenumberFieldInfo;
    @FXML
    private TextField addressFieldInfo;
    @FXML
    Button profileSave;
    @FXML
    Button profileReset;
    private SurveyorController surveyorController;


    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    @FXML
    public void surveyorLogout(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(surveyorHomeScene);
        System.out.println("Insurance Surveyor logout");
    }
    public void processClaim(ActionEvent event) throws IOException {
        claimController.processClaim(event);
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
        String[] information = surveyorController.retrieveInformation().split("\n");
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
