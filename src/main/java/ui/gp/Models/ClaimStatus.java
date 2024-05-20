package ui.gp.Models;

public enum ClaimStatus {
    Approved, Rejected, Pending, nextStage;
    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}
