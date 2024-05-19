package ui.gp.SceneController.Function;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Role;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Policy.OwnerHomeController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class SceneUtil {
    private static AnchorPane currentScene;

    public static void itemFilter(ObservableList<User> items, TableView<User> tableView) {
        FilteredList<User> filteredItems = new FilteredList<>(items, b -> true);

        SortedList<User> sortedItems = new SortedList<>(filteredItems);
        sortedItems.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedItems);
    }

    public static void setCurrentScene(AnchorPane currentScene) {
        SceneUtil.currentScene = currentScene;
    }

    public static void logout(AnchorPane pane) throws IOException {
        Optional<ButtonType> confirmation = confirmationDialog(
                "Logout",
                "Are you sure you want to logout?",
                "You will be redirected to the home page"
        );

        if (confirmation.get() == ButtonType.OK) {
            setCurrentScene(pane);
            changeScene(pane, "/ui/gp/Scene/Login.fxml");

            // luu lai thong tin
        }
    }

    private static void changeScene(AnchorPane currentScene, String fxmlPath) throws IOException {
        AnchorPane nextScene = FXMLLoader.load(Objects.requireNonNull(SceneUtil.class.getResource(fxmlPath)));
        changeCleanScene(currentScene, nextScene);
    }


    private static void changeCleanScene(AnchorPane currentScene, AnchorPane nextScene) {
        currentScene.getChildren().removeAll();
        currentScene.getChildren().setAll(nextScene);
    }


    private static Optional<ButtonType> confirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    public static ObservableList<User> getDependentItemList() {
        ObservableList<User> dependentList = getAllChildPolicyOwner();
        return dependentList.filtered(user -> user.getRole().equals("Dependent"));
    }

    public static ObservableList<User> getPHItemList() {
        ObservableList<User> phList = getAllChildPolicyOwner();
        return phList.filtered(user -> user.getRole().equals("Policy Holder"));
    }

    private static ObservableList<User> getAllChildPolicyOwner() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT * FROM Users u " +
                    "JOIN policyholder p ON u.id = p.dependentid OR u.id = p.policyholderid " +
                    "WHERE p.policyholderid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            OwnerHomeController ownerHomeController = new OwnerHomeController();
            ownerHomeController.populatePolicyOwnerTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }
}
