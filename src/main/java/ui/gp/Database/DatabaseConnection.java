package ui.gp.Database;

import ui.gp.Models.Role;

import java.sql.*;

import ui.gp.Models.Users.*;
import ui.gp.SceneController.Function.LoadingSceneController;

public class DatabaseConnection
{
    private static final String URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres?user=postgres.jmutagfxtwjwqftxmqob&password=Wataru0109911";
    private static final String username = "postgres.jmutagfxtwjwqftxmqob";
    private static final String password = "Wataru0109911";
    private LoadingSceneController loadingSceneController;
    private static Connection connection;
    private  static DatabaseConnection instance;

      private DatabaseConnection() {
          this.loadingSceneController = new LoadingSceneController();
          connection();
      }

      public static DatabaseConnection getInstance() {
          if (instance == null) {
              instance = new DatabaseConnection();
          }
          return instance;
      }


    public void openLoadingScene()
    {
        loadingSceneController.openLoadingScene();
    }
    public void closeLoadingScene()
    {
        loadingSceneController.closeLoadingScene();
    }

    public static Connection connection()
    {
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

    public void disconnect()
    {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  Connection getConnection()
    {
        return connection;

    }

    public ResultSet getDependentData(String username, String password)
    {
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

    public void performOperation() { //dummy method to simulate loading screen
        openLoadingScene();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        closeLoadingScene();
    }

    public boolean isLoadingScreenDisplayed() {
        return loadingSceneController.isLoadingScreenDisplayed();
    }

    public User getUser(String username, String password) {
        User user = null;
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String fullname = resultSet.getString("fullname");
                    String email = resultSet.getString("email");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    String address = resultSet.getString("address");
                    Role role = Role.valueOf(resultSet.getString("role"));

                switch (role)
                {
                    case Dependent:
                        user = new Dependent(id, username, password, role, fullname, email, phoneNumber, address);
                        break;
                    case System_Admin:
                        user = new SystemAdmin(id, username, password, role, fullname, email, phoneNumber, address);
                        break;
                    case Insurance_Surveyor:
                        user = new InsuranceSurveyor(id, username, password, role, fullname, email, phoneNumber, address);
                        break;
                    case Insurance_Manager:
                        user = new InsuranceManager(id, username, password, role, fullname, email, phoneNumber, address);
                        break;
                    case Policy_Holder:
                        user = new PolicyHolder(id, username, password, role, fullname, email, phoneNumber, address);
                        break;
                    case Policy_Owner:
                        user = new PolicyOwner(id, username, password, role, fullname, email, phoneNumber, address);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}