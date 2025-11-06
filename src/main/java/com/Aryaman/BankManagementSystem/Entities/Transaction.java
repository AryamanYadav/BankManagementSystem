package com.Aryaman.BankManagementSystem.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int txnId;

    private Long txnAmount;

    private String txnType;  // DEPOSIT, WITHDRAW, TRANSFER

    private String txnStatus; // SUCCESS, FAILED

    private LocalDateTime txnDateTime;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    // Optional: for transfers
    private String receiverAccountId;

    // Getters and setters
    public int getTxnId() {
        return txnId;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public Long getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(Long txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public LocalDateTime getTxnDateTime() {
        return txnDateTime;
    }

    public void setTxnDateTime(LocalDateTime txnDateTime) {
        this.txnDateTime = txnDateTime;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(String receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }
}
