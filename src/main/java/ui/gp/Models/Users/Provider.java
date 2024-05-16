package ui.gp.Models.Users;

import ui.gp.Models.Role;

public class Provider extends User {
    public Provider(String id, String username, String password,
                    Role role, String fullName, String email,
                    String phoneNumber, String address) {
        super(id, username, password, role, fullName, email, phoneNumber, address);
    }
}
