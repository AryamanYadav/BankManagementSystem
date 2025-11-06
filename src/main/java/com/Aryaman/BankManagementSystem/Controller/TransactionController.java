package com.Aryaman.BankManagementSystem.Controller;

import com.Aryaman.BankManagementSystem.Payloads.TransactionDto;
import com.Aryaman.BankManagementSystem.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Deposit
    @PutMapping("/deposit/{accId}/{amt}")
    public ResponseEntity<TransactionDto> depositAmount(
            @PathVariable String accId,
            @PathVariable Long amt) {
        TransactionDto result = transactionService.deposit(accId, amt);
        return ResponseEntity.ok(result);
    }

    // Withdraw
    @PutMapping("/withdraw/{accId}/{amt}")
    public ResponseEntity<TransactionDto> withdrawAmount(
            @PathVariable String accId,
            @PathVariable Long amt) {
        TransactionDto result = transactionService.withdraw(accId, amt);
        return ResponseEntity.ok(result);
    }

    // Transfer
    @PutMapping("/transfer/{senderId}/{receiverId}/{amt}")
    public ResponseEntity<TransactionDto> transferAmount(
            @PathVariable String senderId,
            @PathVariable String receiverId,
            @PathVariable Long amt) {
        TransactionDto result = transactionService.transfer(senderId, receiverId, amt);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/accBalance/{accountNumber}")
    public ResponseEntity<TransactionDto> fetchAccountBalance(@PathVariable("accountNumber") String accountNumber){
        TransactionDto transactionDto = this.transactionService.accountBalanceCheck(accountNumber);
        return ResponseEntity.ok(transactionDto);
    }
}
