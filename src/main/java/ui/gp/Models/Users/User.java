package ui.gp.Models.Users;

import ui.gp.Models.Role;

public class User {
    private String id;
    private String username;
    private String password;
    private Role role;
    private  String fullName;
    private String email;
    private String phoneNumber;
    private String address;

    public User(String id, String username, String password, Role role,
                String fullName, String email, String phoneNumber, String address)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
