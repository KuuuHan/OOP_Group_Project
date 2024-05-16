package ui.gp.Models.Users;

import ui.gp.Models.*;

public abstract class User
{
    private String id;
    private String username;
    private String password;
    private Role role;
    private  String fullname;
    private String email;
    private String phonenumber;
    private String address;

    public User(String id, String username, String password, Role role, String fullname, String email, String phonenumber, String address)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
