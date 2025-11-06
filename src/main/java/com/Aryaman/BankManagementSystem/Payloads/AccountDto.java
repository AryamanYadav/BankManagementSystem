package com.Aryaman.BankManagementSystem.Payloads;

import com.Aryaman.BankManagementSystem.Entities.User;

public class AccountDto {

    private String accType;

    private Long accBalance;

    private boolean accStatus;


    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public Long getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(Long accBalance) {
        this.accBalance = accBalance;
    }



    public boolean isAccStatus() {
        return accStatus;
    }

    public void setAccStatus(boolean accStatus) {
        this.accStatus = accStatus;
    }
}
