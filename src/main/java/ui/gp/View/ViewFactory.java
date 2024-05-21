package ui.gp.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.gp.Models.Claim;
import ui.gp.Models.Users.*;
import ui.gp.SceneController.Controllers.*;
import ui.gp.SceneController.Function.DependentAddingFormController;
import ui.gp.SceneController.Function.*;
import ui.gp.Database.DatabaseConnection;
import ui.gp.SceneController.Manager.AdminHomeController;
import ui.gp.SceneController.Manager.ManagerHomeController;
import ui.gp.SceneController.Manager.SurveyorHomeController;
import ui.gp.SceneController.Policy.DependentsHomeController;
import ui.gp.SceneController.Policy.HolderHomeController;
import ui.gp.SceneController.Policy.OwnerHomeController;
import ui.gp.Tab.ClaimController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ViewFactory {
    private DatabaseConnection databaseConnection;

    public ViewFactory(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void showLoginScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Login.fxml"));
//        Scene scene = null;
        Parent scene = null;
        try {
//            scene = new Scene(loader.load());
            scene = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Group Project");
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("/ui/gp/appIcon/appIcon.jpeg").toExternalForm()));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void showDependentWindow(User model, AnchorPane homeScene) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/gp/Scene/Policy/Dependents.fxml"));
            AnchorPane root = new AnchorPane();
            loader.setRoot(root);
            root = loader.load();
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);
            DependentsHomeController controller = loader.getController();
            Dependent dependents = (Dependent) model;
            DependentController dependentController = new DependentController(dependents, databaseConnection.getConnection());
            controller.initialize(dependents, dependentController);

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
            PolicyOwnerController policyOwnerController = new PolicyOwnerController(policyOwner, databaseConnection.getConnection());
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
            HolderHomeController controller = loader.getController();
            PolicyHolder policyHolder = (PolicyHolder) model;
            PolicyHolderController policyHolderController = new PolicyHolderController(policyHolder, databaseConnection.getConnection());
            controller.initialize(policyHolder, policyHolderController);

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
            ManagerController managerController = new ManagerController(manager, databaseConnection.getConnection());
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
            SurveyorHomeController controller = loader.getController();
            InsuranceSurveyor surveyor = (InsuranceSurveyor) model;
            SurveyorController surveyorController = new SurveyorController(surveyor, databaseConnection.getConnection());
            controller.initialize(surveyor, surveyorController);
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
            AdminHomeController controller = loader.getController();
            SystemAdmin systemAdmin = (SystemAdmin) model;
            AdminController adminController = new AdminController(systemAdmin, databaseConnection.getConnection());
            controller.initialize(systemAdmin, adminController);

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

    public void showPolicyHolderFormUpdate(User user,String policyOwnerID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/PolicyHolderUpdatingForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update The Policy Holder Information");
            stage.setResizable(false);
            stage.show();
            PolicyHolderUpdatingFormController controller = loader.getController();
            controller.setDatabaseConnection(databaseConnection);
            controller.setUser(user);
            controller.setPolicyOwnerID(policyOwnerID);
            controller.initialise();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPolicyHolderInformation(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/PolicyHolderShowingForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Show The Policy Holder Information");
            stage.setResizable(false);
            stage.show();
            PolicyHolderShowingController controller = loader.getController();
            controller.setDatabaseConnection(databaseConnection);
            controller.setUser(user);
            controller.initialise();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDepenentFormUpdate(User user,String policyOwnerID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/DependentUpdatingForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update The Dependent Information");
            stage.setResizable(false);
            stage.show();

            DependentUpdatingFormController controller = loader.getController();
            controller.setDatabaseConnection(databaseConnection);
            controller.setUser(user);
            controller.setPolicyOwnerID(policyOwnerID);
            controller.initialise();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showClaimForm(List<Customer> beneficiariesList, String user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/ClaimAdd.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Claim");
            stage.setResizable(false);
            stage.show();
            ClaimController controller = loader.getController();
            controller.setPolicyOwner(user);
            controller.setDatabaseConnection(databaseConnection);
            controller.setBeneficiariesList(beneficiariesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSpecificClaimForm(Claim claim)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/ShowClaims.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Claim");
            stage.setResizable(false);
            stage.show();
            ShowClaimController controller = loader.getController();
            controller.setDatabaseConnection(databaseConnection);
            controller.setViewFactory(new ViewFactory(databaseConnection));
            controller.setClaim(claim);
            controller.initialise();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showImage(String imageName) {
        File documentsDirectory = new File("src/main/resources/Documents/");
        File[] files = documentsDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().contains(imageName)) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/ShowImage.fxml"));
                        Parent root = loader.load();
                        ImageView imageView = (ImageView) root.lookup("#imageView");
                        imageView.setImage(new Image(new FileInputStream(file)));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                        break; // Exit the loop after showing the first matching image
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void ShowClaimFormUpdate(Claim claim, String policyOwnerID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/ClaimUpdatingForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update The Claim Information");
            stage.setResizable(false);
            stage.show();
            ClaimUpdatingFormController controller = loader.getController();
            controller.setDatabaseConnection(databaseConnection);
            controller.setClaim(claim);
            controller.setPolicyOwnerID(policyOwnerID);
            controller.initialise();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDependentInformation(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/DependentShowingForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Show The Dependent Information");
            stage.setResizable(false);
            stage.show();

            DependentShowingController controller = loader.getController();
            controller.setDatabaseConnection(databaseConnection);
            controller.setUser(user);
            controller.initialise();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
