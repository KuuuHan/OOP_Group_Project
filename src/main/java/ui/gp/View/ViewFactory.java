package ui.gp.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.gp.ApplicationStart;
import ui.gp.Models.Model;
import ui.gp.Models.Users.Dependent;
import ui.gp.SceneController.Controllers.DependentController;
import ui.gp.SceneController.Policy.DependentsHomeController;

import java.io.IOException;

public class ViewFactory {

    public void showLoginScene()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Login.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(loader.load());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Group Project");
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("/ui/gp/appIcon/appIcon.jpeg").toExternalForm()));
        stage.setScene(scene);
        stage.show();
    }

    public void showDependentWindow(Model model,AnchorPane homeScene)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/gp/Scene/Policy/Dependents.fxml"));
            AnchorPane root = new AnchorPane();
            loader.setRoot(root);
            root = loader.load();
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);
            DependentsHomeController controller = loader.getController();
            Dependent dependent = model.getDependent();
            DependentController dependentController = new DependentController(dependent);
            controller.initialize(dependent, dependentController);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Login Successful!");
    }

    public void showPolicyOwnerWindow(Model model, AnchorPane homeScene)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/gp/Scene/Policy/PolicyOwner.fxml"));
            AnchorPane root = new AnchorPane();
            loader.setRoot(root);
            root = loader.load();
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Login Successful");
    }

    public void showPolicyHolderWindow(Model model, AnchorPane homeScene) {
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

    public void showInsuranceManagerWindow(Model model, AnchorPane homeScene)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/gp/Scene/Manager/Manager.fxml"));
            AnchorPane root = new AnchorPane();
            loader.setRoot(root);
            root = loader.load();
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Login Successful");
    }

    public void showInsuranceSurveyorWindow(Model model, AnchorPane homeScene) {
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

    public void showSystemAdminWindow(Model model, AnchorPane homeScene)
    {
        try
        {
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
}