import org.junit.jupiter.api.Test;
import ui.gp.Database.DatabaseConnection;
import ui.gp.SceneController.Function.PolicyHolderAddingFormController;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AddPolicyOwnerTableTest
{
    @Test
    void testAddPolicyOwner() {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        PolicyHolderAddingFormController controller = new PolicyHolderAddingFormController();
        controller.setDatabaseConnection(databaseConnection);

        String policyHolderID = "testPolicyHolderID";
        String policyOwnerID = "testPolicyOwnerID";

        assertDoesNotThrow(() -> controller.addPolicyOwner(policyOwnerID, policyHolderID));
    }
}
