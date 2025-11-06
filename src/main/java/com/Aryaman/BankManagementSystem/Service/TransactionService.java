package com.Aryaman.BankManagementSystem.Service;

import com.Aryaman.BankManagementSystem.Payloads.AccountDto;
import com.Aryaman.BankManagementSystem.Payloads.TransactionDto;

public interface TransactionService {

    TransactionDto deposit(String accountNumber, Long txnAmount);

    TransactionDto withdraw(String accountNumber, Long txnAmount);

    TransactionDto transfer(String senderAccId, String receiverAccId, Long txnAmount);

    TransactionDto accountBalanceCheck(String accountNumber);
}
