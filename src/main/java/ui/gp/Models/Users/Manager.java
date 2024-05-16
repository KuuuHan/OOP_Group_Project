package ui.gp.Models.Users;

import ui.gp.Models.Claim;
import ui.gp.Models.Role;

import java.util.List;

public class Manager extends Provider {
    public Manager(String id, String username, String password,
                   Role role, String fullName, String email, String phoneNumber,
                   String address)
    {
        super(id, username, password, Role.Insurance_Manager, fullName,
                email, phoneNumber, address);
    }

    public void approveClaim(Claim claim){

    }

    public void rejectClaim(Claim claim){

    }

    public List<Claim> retrieveAllClaims(){

        return null;
    }

    public List<Customer> retrieveCustomers(){ return null; }

    public Surveyor getSurveyor(String surveyorId){
        return null;
    }
}
