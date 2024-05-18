package ui.gp.Database;

import ui.gp.Models.Role;

import java.sql.*;
import ui.gp.SceneController.Function.LoadingSceneController;

public class DatabaseConnection
{
    private static final String URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres?user=postgres.jmutagfxtwjwqftxmqob&password=Wataru0109911";
    private static final String username = "postgres.jmutagfxtwjwqftxmqob";
    private static final String password = "Wataru0109911";
    private LoadingSceneController loadingSceneController;

    public DatabaseConnection()
    {
        this.loadingSceneController = new LoadingSceneController();
    }

    public void openLoadingScene()
    {
        loadingSceneController.openLoadingScene();
    }
    public void closeLoadingScene()
    {
        loadingSceneController.closeLoadingScene();
    }

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

    public ResultSet getDependentData(String username, String password)
    { //openLoadingScene() and closeLoadingScene() in this method is to call the loading scene when the database is being queried
        openLoadingScene();
        Statement statement;
        ResultSet resultSet = null;
        try
        {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "' AND role = 'Dependent'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        closeLoadingScene();
        return resultSet;
    }

    public ResultSet getAdminData(String username, String password)
    {
        Statement statement;
        ResultSet resultSet = null;
        try
        {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "' AND role = 'System_Admin'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getPolicyHolderData(String username, String password)
    {
        Statement statement;
        ResultSet resultSet = null;
        try
        {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "' AND role = 'Policy_Holder'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getPolicyOwnerData(String username, String password)
    {
        Statement statement;
        ResultSet resultSet = null;
        try
        {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "' AND role = 'Policy_Owner'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getInsuranceManagerData(String username, String password)
    {
        Statement statement;
        ResultSet resultSet = null;
        try
        {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "' AND role = 'Insurance_Manager'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getInsuranceSurveyorData(String username, String password)
    {
        Statement statement;
        ResultSet resultSet = null;
        try
        {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "' AND role = 'Insurance_Surveyor'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public Role getUserRole(String username)
    {
        Connection connection = getConnection();
        Role role = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT role FROM Users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = Role.valueOf(resultSet.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }
}