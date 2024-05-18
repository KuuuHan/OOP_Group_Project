package ui.gp.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.gp.ApplicationStart;
import ui.gp.Models.Model;
import ui.gp.Models.Users.Dependent;
import ui.gp.Models.Users.Manager;
import ui.gp.Models.Users.PolicyOwner;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Controllers.DependentController;
//import ui.gp.SceneController.Function.DependentAddingFormController;
import ui.gp.SceneController.Controllers.ManagerController;
import ui.gp.SceneController.Controllers.PolicyOwnerController;
import ui.gp.SceneController.Function.DependentAddingFormController;
import ui.gp.SceneController.Function.PolicyHolderAddingFormController;
import ui.gp.Database.DatabaseConnection;
import ui.gp.SceneController.Manager.ManagerHomeController;
import ui.gp.SceneController.Policy.DependentsHomeController;
import ui.gp.SceneController.Policy.OwnerHomeController;

import java.io.IOException;
import java.net.PortUnreachableException;

public class ViewFactory {
    private DatabaseConnection databaseConnection;

    public ViewFactory(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void showLoginScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Login.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Group Project");
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("/ui/gp/appIcon/appIcon.jpeg").toExternalForm()));
        stage.setScene(scene);
        stage.show();
    }

    public void showDependentWindow(User model, AnchorPane homeScene) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/gp/Scene/Policy/Dependents.fxml"));
            AnchorPane root = new AnchorPane();
            loader.setRoot(root);
            root = loader.load();

            DependentAddingFormController controller = loader.getController();
            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            controller.setDatabaseConnection(databaseConnection);

            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.show();
//            DependentsHomeController controller = loader.getController();
         //   Dependent dependent = model.getDependent();
        //    DependentController dependentController = new DependentController(dependent);
         //   controller.initialize(dependent, dependentController);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Login Successful!");
    }

    public void showPolicyOwnerWindow(User model, AnchorPane homeScene) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/gp/Scene/Policy/PolicyOwner.fxml"));
            AnchorPane root = new AnchorPane();
            loader.setRoot(root);
            root = loader.load();
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);
            OwnerHomeController controller = loader.getController();
            PolicyOwner policyOwner = (PolicyOwner) model;
            PolicyOwnerController policyOwnerController = new PolicyOwnerController(policyOwner,databaseConnection.getConnection());
            controller.initialize(policyOwner, policyOwnerController);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Login Successful");
    }

    public void showPolicyHolderWindow(User model, AnchorPane homeScene) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/gp/Scene/Policy/PolicyHolder.fxml"));
            AnchorPane root = new AnchorPane();
            loader.setRoot(root);
            root = loader.load();
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Login Successful");
    }

    public void showManagerWindow(User model, AnchorPane homeScene) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/gp/Scene/Manager/Manager.fxml"));
            AnchorPane root = new AnchorPane();
            loader.setRoot(root);
            root = loader.load();
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);
            ManagerHomeController controller = loader.getController();
            Manager manager = (Manager) model;
            ManagerController managerController = new ManagerController(manager,databaseConnection.getConnection());
            controller.initialize(manager, managerController);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Login Successful");
    }

    public void showInsuranceSurveyorWindow(User model, AnchorPane homeScene) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/gp/Scene/Manager/Surveyor.fxml"));
            AnchorPane root = new AnchorPane();
            loader.setRoot(root);
            root = loader.load();
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Login Successful");
    }

    public void showSystemAdminWindow(User model, AnchorPane homeScene) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/gp/Scene/Manager/AdminHome.fxml"));
            AnchorPane root = new AnchorPane();
            loader.setRoot(root);
            root = loader.load();
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Login Successful!");
    }

    public void showPolicyHolderForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/PolicyHolderAddingForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Policy Holder");
            stage.setResizable(false);
            stage.show();

            PolicyHolderAddingFormController controller = loader.getController();
            controller.setDatabaseConnection(databaseConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDependentForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/DependentAddingForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Dependent");
            stage.setResizable(false);
            stage.show();

            DependentAddingFormController controller = loader.getController();
            controller.setDatabaseConnection(databaseConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
