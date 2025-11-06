package com.Aryaman.BankManagementSystem.Controller;

import com.Aryaman.BankManagementSystem.Entities.Account;
import com.Aryaman.BankManagementSystem.Payloads.AccountDto;
import com.Aryaman.BankManagementSystem.Payloads.ApiResponse;
import com.Aryaman.BankManagementSystem.Repository.AccountRepo;
import com.Aryaman.BankManagementSystem.Service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepo accountRepo;

    @PostMapping("/create/user/{userId}")
    public ResponseEntity<AccountDto> createAccount(
            @RequestBody AccountDto accountDto,
            @PathVariable Integer userId) {

        AccountDto createdAccount = accountService.createAccount(accountDto, userId);

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);

    }

    @PutMapping("/update/{accountNumber}")
    public ResponseEntity<AccountDto> updateAccount(
            @RequestBody AccountDto accountDto,
            @PathVariable String accountNumber) {

        AccountDto updatedAccount = accountService.updateAccount(accountDto, accountNumber);
        return ResponseEntity.ok(updatedAccount);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable String accountNumber) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.ok(new ApiResponse("Account deleted successfully!", true));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/{accountNumber}")
    public ResponseEntity<AccountDto> getAccountDetails(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountDetails(accountNumber));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/changeStatus/{accNumber}")
    public ResponseEntity<ApiResponse> setAccountStatus(@PathVariable String accNumber){
        Account account = this.accountRepo.findById(accNumber).orElseThrow(RuntimeException::new);
        boolean accStatus = account.isAccStatus();
        if(accStatus){
            account.setAccStatus(false);
        }
        if(!accStatus){
            account.setAccStatus(true);
        }
        accountRepo.save(account);

        return ResponseEntity.ok(new ApiResponse("User Status Changed Successfully!",true));
    }
}
