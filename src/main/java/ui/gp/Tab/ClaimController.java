package ui.gp.Tab;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClaimController
{
    public void addItemOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/Claim.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}
