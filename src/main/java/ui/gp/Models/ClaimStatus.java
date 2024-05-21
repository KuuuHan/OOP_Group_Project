package ui.gp.Models;

public enum ClaimStatus {
    Approved, Rejected, Pending, NextStage;
    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}
