package ui.gp.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    private static final String URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres?user=postgres.jmutagfxtwjwqftxmqob&password=Wataru0109911";
    private static final String username = "postgres.jmutagfxtwjwqftxmqob";
    private static final String password = "Wataru0109911";

    public static Connection getConnection()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(URL,username,password);
            System.out.println("Connection to database successful");
        } catch (SQLException e)
        {
            System.out.println("Connection to database failed");
            e.printStackTrace();
        }
        return connection;
    }
}