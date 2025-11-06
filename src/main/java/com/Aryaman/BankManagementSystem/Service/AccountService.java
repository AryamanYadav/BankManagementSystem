package com.Aryaman.BankManagementSystem.Service;

import com.Aryaman.BankManagementSystem.Payloads.AccountDto;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    AccountDto createAccount(AccountDto accountDto,Integer userId);

    AccountDto updateAccount(AccountDto accountDto,String accountNumber);

    void deleteAccount(String accountNumber);

    AccountDto getAccountDetails(String accountNumber);

  //  AccountDto changeAccStatus(AccountDto accountDto , String accNumber);



}
