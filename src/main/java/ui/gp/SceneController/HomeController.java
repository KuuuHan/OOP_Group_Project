package ui.gp.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HomeController {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane homeScene;
    @FXML
    private TextField homeLoginName;
    @FXML
    private PasswordField homeLoginPassword;
    @FXML
    private Label statusText;
    private FXMLLoader loader;
    private Parent root;
    Stage stage;

    @FXML
    public void login(ActionEvent loginAction) throws IOException {
        String username = homeLoginName.getText();
        String password = homeLoginPassword.getText();

        if (!username.isEmpty() && !password.isEmpty()){
            // cho nay cho reset mat khau ho
            // search tai khoan o day

            // cho nay de tu dong login cho may cai da duoc mac dinh roi
            if (username.equals("admin") && password.equals("admin")){
                try {
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/ui/gp/Scene/Admin/AdminHome.fxml"));
                    AnchorPane root = new AnchorPane();
                    loader.setRoot(root);
                    root = loader.load();
                    getLoginName(username);
                    stage = (Stage) homeScene.getScene().getWindow();
                    stage.getScene().setRoot(root);
                } catch (IOException e){
                    e.printStackTrace();
                }
                System.out.println("Login Successful");

            } else
                if (username.equals("holder") && password.equals("holder")) {
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Customer/PolicyHolder.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        getLoginName(username);
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                System.out.println("Login Successful");

            } else
                if (username.equals("dependents") && password.equals("dependents")) {
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Customer/Dependents.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        getLoginName(username);
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("Login Successful");

            } else
                if (username.equals("owner") && password.equals("owner")) {
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Customer/PolicyOwner.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        getLoginName(username);
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("Login Successful");

            } else
                if (username.equals("manager") && password.equals("manager")) {
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Customer/Manager.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        getLoginName(username);
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("Login Successful");

            } else
                if (username.equals("surveyor") && password.equals("surveyor")) {
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Customer/Surveyor.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        getLoginName(username);
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("Login Successful");

                } else {
                loginErrorMsg();
            }
            // --------------------------------------


            // khuc nay de login ne (link voi database hoac la lam txt luon idk?

        } else{
            loginErrorMsg();
        }
    }

    public void exit(ActionEvent exitAction){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit");
        exitAlert.setHeaderText("You are about to exit the program");
        exitAlert.setContentText("Are you sure you want to exit?");

        if (exitAlert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) homeScene.getScene().getWindow();
            stage.close();
        }
    }

    private void loginErrorMsg(){
        statusText.setText("Invalid username or password. Please try again.");
    }

//    private void getLoginName(String name){
//        HolderHomeController policyHolderHomeController = loader.getController();
//        if (policyHolderHomeController != null){
//            policyHolderHomeController.bannerNameView(name);
//        }
//    }

    private void getLoginName(String name) {
        try {
            // Convert the username to CamelCase to match the controller class name
            String className = name.substring(0, 1).toUpperCase() + name.substring(1) + "HomeController";

            // Get the controller class dynamically
            Class<?> controllerClass = Class.forName("ui.gp.SceneController.Customer." + className);

            // Get the controller instance from the FXMLLoader
            Object controller = loader.getController();

            // Check if the controller is an instance of the expected controller class
            if (controllerClass.isInstance(controller)) {
                // Cast the controller to the expected controller class and call the bannerNameView method
                Method method = controllerClass.getMethod("bannerNameView", String.class);
                method.invoke(controller, name);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
