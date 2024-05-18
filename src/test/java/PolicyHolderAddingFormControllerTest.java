import org.junit.jupiter.api.Test;
import ui.gp.Database.DatabaseConnection;
import ui.gp.SceneController.Function.PolicyHolderAddingFormController;

import static org.junit.jupiter.api.Assertions.*;

class PolicyHolderAddingFormControllerTest {

    @Test
    void testAddPolicyHolder() {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        PolicyHolderAddingFormController controller = new PolicyHolderAddingFormController();
        controller.setDatabaseConnection(databaseConnection);

        String policyHolderID = controller.addPolicyHolder("fullname", "username", "password", "email", "phoneNumber", "address");

        assertNotNull(policyHolderID, "PolicyHolderID should not be null");
    }

    @Test
    void testAddPolicyOwner() {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        PolicyHolderAddingFormController controller = new PolicyHolderAddingFormController();
        controller.setDatabaseConnection(databaseConnection);

        String policyHolderID = controller.addPolicyHolder("fullname", "username", "password", "email", "phoneNumber", "address");
        String policyOwnerID = "id_test";
        controller.addPolicyOwner(policyOwnerID, policyHolderID);

        assertNotNull(policyOwnerID, "PolicyOwnerID should not be null");
    }
}