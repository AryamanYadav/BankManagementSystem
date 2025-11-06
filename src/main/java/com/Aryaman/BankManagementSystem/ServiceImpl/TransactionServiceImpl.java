package com.Aryaman.BankManagementSystem.ServiceImpl;

import com.Aryaman.BankManagementSystem.Entities.Account;
import com.Aryaman.BankManagementSystem.Entities.Transaction;
import com.Aryaman.BankManagementSystem.Exceptions.ResourceNotFoundException;
import com.Aryaman.BankManagementSystem.Payloads.AccountDto;
import com.Aryaman.BankManagementSystem.Payloads.TransactionDto;
import com.Aryaman.BankManagementSystem.Repository.AccountRepo;
import com.Aryaman.BankManagementSystem.Repository.TransactionRepo;
import com.Aryaman.BankManagementSystem.Service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TransactionDto deposit(String accountNumber, Long txnAmount) {
        Account account = accountRepo.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException());
        boolean accStatus = account.isAccStatus();

        if(accStatus==true) {

            // Update balance
            account.setAccBalance(account.getAccBalance() + txnAmount);
            accountRepo.save(account);

            // Record transaction
            Transaction transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setTxnType("DEPOSIT");
            transaction.setTxnAmount(txnAmount);
            transaction.setTxnStatus("SUCCESS");
            transaction.setTxnDateTime(LocalDateTime.now());
            transaction = transactionRepo.save(transaction);

            return modelMapper.map(transaction, TransactionDto.class);
        }
        else {
            return null;
        }
    }

    @Override
    public TransactionDto withdraw(String accountNumber, Long txnAmount) {
        Account account = accountRepo.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException());

        boolean accStatus = account.isAccStatus();

        if(accStatus==true)
        {

        if (account.getAccBalance() < txnAmount) {
            Transaction transaction = new Transaction();
            transaction.setTxnStatus("Insufficient Balance");
            //throw new RuntimeException("Insufficient balance!");
            return modelMapper.map(transaction, TransactionDto.class);

        }

        // Update balance
        account.setAccBalance(account.getAccBalance() - txnAmount);
        accountRepo.save(account);

        // Record transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTxnType("WITHDRAW");
        transaction.setTxnAmount(txnAmount);
        transaction.setTxnStatus("SUCCESS");
        transaction.setTxnDateTime(LocalDateTime.now());
        transaction = transactionRepo.save(transaction);

        return modelMapper.map(transaction, TransactionDto.class);

        }
        else {
            return null;
        }
    }

    @Override
    public TransactionDto transfer(String senderAccId, String receiverAccId, Long txnAmount) {
        Account sender = accountRepo.findById(senderAccId)
                .orElseThrow(() -> new RuntimeException());

        Account receiver = accountRepo.findById(receiverAccId)
                .orElseThrow(() -> new RuntimeException());

        boolean senderAccStatus= sender.isAccStatus();
        boolean receiverAccStatus= receiver.isAccStatus();

        if(senderAccStatus==true && receiverAccStatus==true) {

            if (sender.getAccBalance() < txnAmount) {
                Transaction transaction = new Transaction();
                transaction.setTxnStatus("Insufficient Balance");
                return modelMapper.map(transaction, TransactionDto.class);

                //throw new RuntimeException("Insufficient balance in sender account!");
            }

            // Update balances
            sender.setAccBalance(sender.getAccBalance() - txnAmount);
            receiver.setAccBalance(receiver.getAccBalance() + txnAmount);
            accountRepo.save(sender);
            accountRepo.save(receiver);

            // Record transaction
            Transaction transaction = new Transaction();
            transaction.setAccount(sender);
            transaction.setTxnType("TRANSFER");
            transaction.setTxnAmount(txnAmount);
            transaction.setTxnStatus("SUCCESS");
            transaction.setTxnDateTime(LocalDateTime.now());
            transaction.setReceiverAccountId(receiverAccId);
            transaction = transactionRepo.save(transaction);

            return modelMapper.map(transaction, TransactionDto.class);
        }
        else {
            return null;
        }
    }

    @Override
    public TransactionDto accountBalanceCheck(String accountNumber) {
        Account account = this.accountRepo.findById(accountNumber)
                .orElseThrow(()-> new RuntimeException());

        boolean accStatus = account.isAccStatus();

        if(accStatus==true) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTxnAmount(account.getAccBalance());
            return transactionDto;
        }
        else {
            return null;
        }

    }


}
