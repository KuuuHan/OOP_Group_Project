package ui.gp.SceneController.Function;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
import ui.gp.View.ViewFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowClaimController {
    public TextField ClaimID;
    public TextField InsuredPersonID;
    public TextField ClaimDate;
    public TextField ExamDate;
    public TextField ClaimAmount;
    public TextField ClaimStatus;
    public TextField BankNumber;
    public TextField BankName;
    public TextField BankOwnerName;
    public TextField CardNumber;
    public TextField PolicyOwnerID;
    public TextField ExpirationDate;
    public TextField CardHolderName;
    @FXML
    private ChoiceBox<String> imagebox;
    private ViewFactory view;

    private DatabaseConnection databaseConnection;
    private Claim claim;

    public void setDatabaseConnection(DatabaseConnection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }
    public void setViewFactory(ViewFactory view) {
        this.view = new ViewFactory(DatabaseConnection.getInstance());
    }


    public void setClaim(Claim claim)
    {
        this.claim = claim;
    }

    public void initialise() {
        String[] information = retrieveClaimInformation().split(",");
        ClaimID.setText(information[0]);
        InsuredPersonID.setText(information[1]);
        ClaimDate.setText(information[2]);
        ExamDate.setText(information[3]);
        ClaimAmount.setText(information[4]);
        ClaimStatus.setText(information[5]);
        BankNumber.setText(information[6]);
        BankName.setText(information[7]);
        BankOwnerName.setText(information[8]);
        CardNumber.setText(information[9]);
        PolicyOwnerID.setText(information[10]);
        ExpirationDate.setText(information[11]);
        CardHolderName.setText(information[12]);
        List<String> imageNames = retrievelistofimage(claim.getId());
        List <String> imageNameBox = imageNames;
        for (String imageName : imageNameBox) {
            imageName = imageName.split("\\.")[0] + ".pdf";
            imagebox.getItems().add(imageName);
        }
    }
    @FXML
    public void ShowimageonAction() {
        String selectedImageName = imagebox.getValue();
        if (selectedImageName != null) {
            view.showImage(selectedImageName);
        }
    }





    private String retrieveClaimInformation() {
        return claim.getId() + "," +
                claim.getInsuredPersonID() + "," +
                claim.getDate() + "," +
                claim.getExamDate() + "," +
                claim.getClaimAmount() + "," +
                claim.getStatus() + "," +
                claim.getReceiverBankingNumber() + "," +
                claim.getReceiverBankingName() + "," +
                claim.getReceiverHolderName() + "," +
                claim.getCardNumber() + "," +
                claim.getPolicy_owner_insuranceid() + "," +
                claim.getExpireDate() + "," +
                claim.getReceiverHolderName();
    }
    private List<String> retrievelistofimage(String claimId) {
        String query = "SELECT documentname FROM documents WHERE claimid = ?";
        List<String> documentNames = new ArrayList<>();
        try {
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, claimId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                documentNames.add(resultSet.getString("documentname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();


        }
        return documentNames;
    }

}
