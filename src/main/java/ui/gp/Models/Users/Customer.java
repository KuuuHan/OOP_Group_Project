package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.List;

public abstract class Customer extends User
{
    private InsuranceCard insuranceCard;
    private List <Claim> claims;

    public Customer(String id, String username, String password, Role role, String fullname, String email, String phonenumber, String address, InsuranceCard insuranceCard, List<Claim> claims)
    {
        super(id, username, password, role, fullname, email, phonenumber, address);
        this.insuranceCard = insuranceCard;
        this.claims = claims;
    }
}
