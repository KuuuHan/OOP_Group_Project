import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import ui.gp.Models.Users.Dependent;
import ui.gp.SceneController.Customer.Dependent.DependentController;

public class retrieveInformation_DependentTest
{
    @Test
    public void testRetrieveInformation() {
        Dependent dependent = new Dependent("","","",null,"","","","",null,null,null,null);
        dependent.setId("c-1234567");
        dependent.setFullname("Junkynet Kangtark");
        dependent.setUsername("spjk123");
        dependent.setPassword("1111");
        dependent.setEmail("spjk@example.com");
        dependent.setPhonenumber("0985654555");
        dependent.setAddress("123 Main St");

        DependentController dependentController = new DependentController(dependent);

        String expected = "ID: c-1234567\nFull Name: Junkynet Kangtark\nUsername: spjk123\nPassword: 1111\nEmail: spjk@example.com\nPhone Number: 0985654555\nAddress: 123 Main St";
        String actual = dependentController.retrieveInformation();
        assertEquals(expected, actual);
    }
}