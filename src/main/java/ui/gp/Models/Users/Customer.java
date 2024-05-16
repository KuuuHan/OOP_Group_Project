package ui.gp.Models.Users;

import ui.gp.Models.Claim;
import ui.gp.Models.InsuranceCard;
import ui.gp.Models.Role;

import java.util.List;

public abstract class Customer extends User {
    private InsuranceCard insuranceCard;
    private List<Claim> claims;

    public Customer(String id, String username, String password,
                    Role role, String fullName, String email,
                    String phoneNumber, String address,
                    InsuranceCard insuranceCard, List<Claim> claims)
    {
        super(id, username, password, role, fullName, email, phoneNumber, address);
        this.insuranceCard = insuranceCard;
        this.claims = claims;
    }
}
