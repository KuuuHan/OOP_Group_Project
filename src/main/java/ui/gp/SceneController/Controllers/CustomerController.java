package ui.gp.SceneController.Controllers;

import ui.gp.Models.Users.*;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    /**
     * This function returns a list of all Regular instances in the map
     */
    public List<PolicyHolder> getRegularList(){
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
    public List<PolicyOwner> getVIPList(){
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
    public List<InsuranceSurveyor> getGuestList(){
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
