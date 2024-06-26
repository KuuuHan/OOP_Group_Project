package ui.gp.Models;

import javafx.scene.control.Button;
import ui.gp.Models.Users.*;

import java.util.Date;
import java.util.Set;

public class Claim {
    private String id;
    private Date date;
    private Customer insuredPerson;
    private String cardNumber;
    private Date examDate;
    private Set<String> documents;
    private double claimAmount;
    private ClaimStatus status;
    private ReceiverBankingInfo receiverBankingInfo;

    public Claim(String id, Date claimDate, Customer insuredPerson, String cardNumber,
                 Date examDate, Set<String> documents, double claimAmount,
                 ClaimStatus status, ReceiverBankingInfo receiverBankingInfo)
    {
        this.id = id;
        this.date = claimDate;
        this.insuredPerson = insuredPerson;
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverBankingInfo = receiverBankingInfo;
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

    public Customer getInsuredPerson() {
        return insuredPerson;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Set<String> getDocuments() {
        return documents;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public ReceiverBankingInfo getReceiverBankingInfo() {
        return receiverBankingInfo;
    }
}
