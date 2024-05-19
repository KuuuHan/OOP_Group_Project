package ui.gp.Models;

public enum Role {
    Policy_Owner, Policy_Holder, Insurance_Surveyor, Insurance_Manager, System_Admin, Dependent;

    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}
