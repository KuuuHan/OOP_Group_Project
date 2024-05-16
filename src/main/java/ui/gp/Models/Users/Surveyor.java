package ui.gp.Models.Users;


import ui.gp.Models.Claim;
import ui.gp.Models.Role;

import java.util.List;

public class Surveyor extends Provider {
    public Surveyor(String id, String username, String password,
                    Role role, String fullName, String email,
                    String phoneNumber, String address)
    {
        super(id, username, password, Role.Insurance_Surveyor, fullName, email, phoneNumber, address);
    }

    public void requireClaimInfo(Claim claimID){

    }

    public void proposeClaimToManager(Claim claim, Manager manager){

    }

    public List<Claim> retrieveAllClaims(){

        return null;
    }

    public List<Customer> retrieveCustomers(){ return null; }
}
