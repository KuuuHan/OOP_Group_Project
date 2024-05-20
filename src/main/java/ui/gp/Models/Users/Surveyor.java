package ui.gp.Models.Users;

import ui.gp.Models.*;

public class Surveyor extends User {

    public Surveyor(String id, String username, String password, Role role, String fullname, String email, String phonenumber, String address) {
        super(id, username, password, role, fullname, email, phonenumber, address);
    }
}
