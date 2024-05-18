package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.List;

public class Dependent extends Customer
{

    public Dependent(String id, String username, String password, Role role,
                     String fullname, String email, String phonenumber, String address)
    {
        super(id, username, password, Role.Dependent, fullname, email, phonenumber, address);
    }

    public String getId() {
        return super.getId();
    }

    public String getFullname() {
        return super.getFullname();
    }

    public String getEmail() {
        return super.getEmail();
    }

    public String getPhonenumber() {
        return super.getPhonenumber();
    }

    public String getAddress() {
        return super.getAddress();
    }


}