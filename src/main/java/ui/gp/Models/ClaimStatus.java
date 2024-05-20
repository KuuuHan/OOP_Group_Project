package ui.gp.Models;

public enum ClaimStatus {
    Approved, Rejected, Pending;
    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}
