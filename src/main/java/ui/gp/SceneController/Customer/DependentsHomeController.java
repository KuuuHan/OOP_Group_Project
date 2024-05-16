package ui.gp.SceneController.Customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import ui.gp.SceneController.Customer.Dependents.*;
import ui.gp.SceneController.SceneUtil;
import java.io.IOException;

public class DependentsHomeController {
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane dependentsHomeScene;
    @FXML
    TabPane dependentsTabPane;
    private ClaimViewController claimViewController;
    private InformationViewController informationViewController;


    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    public void dependentsLogout(ActionEvent logoutAction) throws IOException {
        System.out.println("Dependents logout");
        SceneUtil.logout(dependentsHomeScene);
    }

//    public void initialize(){
//        try{
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("/ui/gp/Scene/Customer/Dependents/claim.fxml"));
//
//            Tab claimTab = new Tab("Claim View");
//            claimTab.setContent(fxmlLoader.load());
//            claimViewController = fxmlLoader.getController();
//
//            dependentsTabPane.getTabs().add(claimTab);
//        } catch (IOException e){
//            System.out.println(e);
//        }
//
//    }
}

