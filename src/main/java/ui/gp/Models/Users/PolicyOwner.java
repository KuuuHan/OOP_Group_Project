package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.List;
import java.util.ArrayList;

public class PolicyOwner extends Customer
{

    public PolicyOwner(String id, String username, String password, Role role,
                       String fullname, String email, String phonenumber, String address)
    {
        super(id, username, password, Role.Policy_Owner, fullname, email, phonenumber, address);
    }

}
