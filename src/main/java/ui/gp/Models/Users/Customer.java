package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.List;

public  class Customer extends User
{
    public Customer(String id, String username, String password, Role role, String fullname, String email, String phonenumber, String address)
    {
        super(id, username, password, role, fullname, email, phonenumber, address);
    }


}
