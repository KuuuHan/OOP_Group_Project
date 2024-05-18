package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.List;
import java.util.ArrayList;

public class PolicyOwner extends Customer
{

    public PolicyOwner(String id, String username, String password, Role role,
                       String fullname, String email, String phonenumber, String address)
    {
        super(id, username, password, Role.Policy_Owner, fullname, email, phonenumber, address,null, null);
    }

    public void fileClaim(String beneficiaryId, Claim claim)
    {

    }

    public void updateClaim(String beneficiaryId, Claim claim)
    {

    }

    public void deleteClaim(String beneficiaryId, String claimId)
    {

    }

    public Claim retrieveClaim(String beneficiaryId, String claimId)
    {

        return null;
    }

    public void addBeneficiary(Customer beneficiary)
    {

    }

    public void updateBeneficiary(String beneficiaryId, Customer updatedBeneficiary)
    {

    }

    public void removeBeneficiary(String beneficiaryId)
    {

    }

    public Customer getBeneficiary(String beneficiaryId)
    {
        return null;
    }

    public double calculateYearlyPayment()
    {
        return 0;
    }
}
