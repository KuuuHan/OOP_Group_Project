package ui.gp.SceneController.Function;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
import ui.gp.Models.Users.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClaimUpdatingFormController {
    @FXML
    private TextField ClaimAmountFieldclaim;
    @FXML
    private TextField ExpirationDateFieldClaim;
    @FXML
    private TextField BankNameFieldClaim;
    @FXML
    private TextField BankHoldernameFieldClaim;
    @FXML
    private TextField BankNumberFieldClaim;
    @FXML
    private TextField IDFieldClaim;

    private DatabaseConnection databaseConnection;
    @FXML
    Button submitButtonUpdateClaim;
    private User user;
    private Claim claim;
    private String policyOwnerID;

    public void setDatabaseConnection(DatabaseConnection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }

    public void setPolicyOwnerID(String policyOwnerID)
    {
        this.policyOwnerID = policyOwnerID;
    }
    public void setUser(User user)
    {
        this.user = user;
    }
    public void initialise()
    { String information = retrieveClaimInformation();
        String[] informationArray = information.split(",");
        ClaimAmountFieldclaim.setText(informationArray[4]);
        ExpirationDateFieldClaim.setText(informationArray[11]);
        BankNameFieldClaim.setText(informationArray[7]);
        BankHoldernameFieldClaim.setText(informationArray[8]);
        BankNumberFieldClaim.setText(informationArray[6]);
        IDFieldClaim.setText(informationArray[0]);

    }
    public void handleClaimSubmitButton(ActionEvent event) {
        String claimAmount = ClaimAmountFieldclaim.getText();
        String expirationDate = ExpirationDateFieldClaim.getText();
        String bankName = BankNameFieldClaim.getText();
        String bankHolderName = BankHoldernameFieldClaim.getText();
        String bankNumber = BankNumberFieldClaim.getText();
        UpdateClaim(claimAmount, expirationDate, bankName, bankHolderName, bankNumber);
        recordHistory(policyOwnerID,"Update info of claim");
        Stage stage = (Stage) submitButtonUpdateClaim.getScene().getWindow();
        stage.close();
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
    private void UpdateClaim(String claimAmount, String expirationDate, String bankName, String bankHolderName, String bankNumber) {
        try {
            double claimAmountDouble = Double.parseDouble(claimAmount);
            // Parse the expirationDate string into a Date object
            // Parse the expirationDate string into a Date object
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date expirationDateDate = formatter.parse(expirationDate);
            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlExpirationDate = new java.sql.Date(expirationDateDate.getTime());
            String query = "UPDATE claim SET claim_amount = ?, expiration_date_insurance = ?, bank_name = ?, card_owner_bank = ?, card_number_bank = ? WHERE id = ?";
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(query);
            statement.setDouble(1, claimAmountDouble);
            statement.setDate(2, sqlExpirationDate);
            statement.setString(3, bankName);
            statement.setString(4, bankHolderName);
            statement.setString(5, bankNumber);
            statement.setString(6, claim.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    private void recordHistory(String userId, String action) {
        try {
            String query = "INSERT INTO historyrecord (userid, action) VALUES (?, ?)";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, userId);
            statement.setString(2, action);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
