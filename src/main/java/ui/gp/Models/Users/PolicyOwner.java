package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.List;
import java.util.ArrayList;

public class PolicyOwner extends Customer
{
    private int numberOfPolicyHolder;
    private int numberOfDependent;

    public PolicyOwner(String id, String username, String password, Role role,
                       String fullname, String email, String phonenumber, String address)
    {
        super(id, username, password, Role.Policy_Owner, fullname, email, phonenumber, address);
    }

    public void policyHolderCount() {
        numberOfPolicyHolder++;
    }

    public void dependentCount(int count) {
        numberOfDependent = count;
    }

    public double calculateYearlyPayment()
    {
        return (numberOfPolicyHolder*600) * (numberOfDependent*360);
    }

}
