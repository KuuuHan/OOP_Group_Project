package ui.gp.Models;

import javafx.scene.control.Button;
import ui.gp.Models.Users.*;

import java.util.Date;
import java.util.Set;

public class Claim {
    private String id;
    private Date date;
    private String insuredPerson;
    private String cardNumber;
    private Date examDate;
    private Set<String> documents;
    private double claimAmount;
    private ClaimStatus status;
    private String bankNumber;

    public Claim(String id, Date claimDate, String insuredPerson,
                 Date examDate, double claimAmount,
                 ClaimStatus status, String bankNumber)
    {
        this.id = id;
        this.date = claimDate;
        this.insuredPerson = insuredPerson;
        this.examDate = examDate;
        this.claimAmount = claimAmount;
        this.status = status;
        this.bankNumber = bankNumber;
    }

    public Date getExamDate() {
        return examDate;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public String getBankNumber() {
        return bankNumber;
    }
}
