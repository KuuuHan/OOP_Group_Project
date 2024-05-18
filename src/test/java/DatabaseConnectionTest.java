import org.junit.jupiter.api.Test;
import ui.gp.Database.DatabaseConnection;


import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void testGetConnection()
    {
        Connection connection = DatabaseConnection.getConnection();
        assertNotNull(connection, "The connection should not be null");
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            fail("Closing connection failed: " + e.getMessage());
        }
    }

    @Test
    public void testLoadingScreenAppear(){
        DatabaseConnection dbConnection = new DatabaseConnection();
        //
        //
        dbConnection.performOperation();

        //
        //
        assertTrue(dbConnection.isLoadingScreenDisplayed());
    }
}
