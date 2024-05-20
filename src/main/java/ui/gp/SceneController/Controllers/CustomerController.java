package ui.gp.SceneController.Controllers;

import ui.gp.Models.Role;
import ui.gp.Models.Users.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    private Customer customer;
    private Connection connection;

    public CustomerController(Customer customer, Connection connection) {
        this.customer = customer;
        this.connection = connection;
    }
    /**
     * This function returns a list of all Regular instances in the map
     */
    public List<PolicyHolder> getPolicyHolderList(){
        List<PolicyHolder> result = new ArrayList<>();
        ArrayList<User> userList = new ArrayList<>();

        for(User user : userList){
            if(user instanceof PolicyHolder){
                result.add((PolicyHolder) user);
            }
        }

        return result;
    }

    /**
     * This function returns a list of VIP instances in the map
     */
    public List<PolicyOwner> getPolicyOwnerList(){
        List<PolicyOwner> result = new ArrayList<>();
        ArrayList<User> userList = new ArrayList<>();

        for(User user : userList){
            if(user instanceof PolicyOwner){
                result.add((PolicyOwner) user);
            }
        }

        return result;
    }

    /**
     * This function returns a list of Guest instances in the map
     */
    public List<InsuranceSurveyor> getSurveyorList(){
        List<InsuranceSurveyor> result = new ArrayList<>();
        ArrayList<User> userList = new ArrayList<>();

        for(User user : userList){
            if(user instanceof InsuranceSurveyor){
                result.add((InsuranceSurveyor) user);
            }
        }

        return result;
    }



}
