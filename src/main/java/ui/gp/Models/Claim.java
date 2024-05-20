package ui.gp.Models;

import javafx.scene.control.Button;
import ui.gp.Models.Users.*;

import java.util.Date;
import java.util.Set;

public class Claim {
    private String id;
    private Date date;
    private String insuredPersonID;
    private String cardNumber;
    private Date examDate;
    private Set<String> documents;
    private double claimAmount;
    private String status;
    private String receiverBankingNumber;
    private String receiverBankingName;
    private String receiverHolderName;
    private String policy_owner_insuranceid;

    private String InsuranceCardHolderName;
    private Date ExpireDate;

    public Claim(String id, Date date, String insuredPersonID, String cardNumber, Date examDate, double claimAmount, String status, String receiverBankingNumber, String receiverBankingName, String receiverHolderName, Date Expdate, String policy_owner_insuranceid, String InsuranceCardHolderName) {
        this.id = id;
        this.date = date;
        this.insuredPersonID = insuredPersonID;
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverBankingNumber = receiverBankingNumber;
        this.receiverBankingName = receiverBankingName;
        this.receiverHolderName = receiverHolderName;
        this.ExpireDate = Expdate;
        this.policy_owner_insuranceid = policy_owner_insuranceid;
        this.InsuranceCardHolderName = InsuranceCardHolderName;

    }

    public Date getExamDate() {
        return examDate;
    }
}
