package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.List;

public abstract class Provider extends User
{
    public Provider(String id, String username, String password, Role role,
                    String fullname, String email, String phonenumber, String address)
    {
        super(id, username, password, role, fullname, email, phonenumber, address);
    }
}
