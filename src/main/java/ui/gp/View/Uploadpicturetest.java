package ui.gp.View;

import ui.gp.Database.DatabaseConnection;
import ui.gp.Tab.UploadController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class Uploadpicturetest {
    Connection connection;
    File file;
    FileInputStream fis;
    String query;
    PreparedStatement ps;


    public Uploadpicturetest() throws FileNotFoundException, SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
        file = new File("C:/Users/ADMIN/Downloads/download.jpg");
        fis = new FileInputStream(file);
        query = "INSERT INTO binary_data (data, f_name, f_type) VALUES (?,?,?)";
        ps = connection.prepareStatement(query);
        ps.setBinaryStream(1, fis, (int) file.length());
        ps.setString(2, file.getName());
        ps.setString(3, "image/jpeg");
        ps.executeUpdate();
    }
    public static void main(String[] args) {
        try {
            new Uploadpicturetest();
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
