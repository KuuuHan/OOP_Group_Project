package ui.gp.Models.Users;

import ui.gp.Models.Claim;
import ui.gp.Models.InsuranceCard;
import ui.gp.Models.Role;

import java.util.List;

public class PolicyHolder extends Customer {
    private PolicyOwner policyOwner;
    private List<Dependents> dependents;

    public PolicyHolder(String id, String username, String password, Role role,
                        String fullName, String email, String phoneNumber,
                        String address, InsuranceCard insuranceCard,
                        List<Claim> claims, PolicyOwner policyOwner,
                        List<Dependents> dependents)
    {
        super(id, username, password, Role.Policy_Holder, fullName, email,
                phoneNumber, address, insuranceCard, claims);
        this.policyOwner = policyOwner;
        this.dependents = dependents;
    }

    public void fileClaim(Claim claim) {

    }

    public void updateClaim(Claim claim) {

    }

    public Claim retrieveClaim(String claimId) { return null; }

    public void fileDependentClaim(Dependents dependent, Claim claim) {

    }

    public void updateDependentClaim(Dependents dependent, Claim claim) {

    }

    public Claim retrieveDependentClaim(Dependents dependent, String claimId) { return null; }

    public void updateInformation(String phone, String address, String email, String password) {

    }

    public String getInformation() { return null; }

    public void updateDependentInformation(Dependents dependent, String phone, String address, String email, String password) {

    }

    public String getDependentInformation(Dependents dependent) { return null; }
}
