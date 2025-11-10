package com.Aryaman.BankManagementSystem.ServiceImpl;

import com.Aryaman.BankManagementSystem.Entities.Account;
import com.Aryaman.BankManagementSystem.Entities.User;
import com.Aryaman.BankManagementSystem.Exceptions.ResourceNotFoundException;
import com.Aryaman.BankManagementSystem.Payloads.AccountDto;
import com.Aryaman.BankManagementSystem.Repository.AccountRepo;
import com.Aryaman.BankManagementSystem.Repository.UserRepo;
import com.Aryaman.BankManagementSystem.Service.AccountService;
import com.Aryaman.BankManagementSystem.Util.AccountNumberGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepo accountRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private AccountNumberGenerator accountNumberGenerator;


    @Override
    public AccountDto createAccount(AccountDto accountDto,Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","UserId",userId));

       String userRole = user.getRole();

       if(userRole.equals("ADMIN"))
       {
            throw new RuntimeException("Admins cannot create their own accounts");
       }

       else {
           Account account = this.modelMapper.map(accountDto, Account.class);
           account.setUser(user);

           String accNum;
           do {
               accNum = accountNumberGenerator.generateAccountNumber();
           } while (accountRepo.existsByAccountNumber(accNum));

           account.setAccountNumber(accNum);


           Account savedAccount = this.accountRepo.save(account);
           return this.modelMapper.map(savedAccount, AccountDto.class);
       }


    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto, String accountNumber) {
        Account account = this.accountRepo.findById(String.valueOf(accountNumber)).orElseThrow(RuntimeException::new);
        boolean accStatus = accountDto.isAccStatus();
        if(accStatus==true) {
            account.setAccType(accountDto.getAccType());
            account.setAccStatus(accountDto.isAccStatus());
            Account updatedAccount = this.accountRepo.save(account);
            AccountDto accountDto1 = this.accountToDto(updatedAccount);
            return accountDto1;
        }
        else{
            throw new RuntimeException("Account Status is Inactive!");
        }
    }

    @Override
    public void deleteAccount(String accountNumber) {
        Account deleteAccount = this.accountRepo.findById(accountNumber).orElseThrow(RuntimeException::new);
        this.accountRepo.delete(deleteAccount);

    }

    @Override
    public AccountDto getAccountDetails(String accountNumber) {
        Account account = this.accountRepo.findById(accountNumber).orElseThrow(RuntimeException::new);
        return this.accountToDto(account);
    }

    /*@Override
    public AccountDto changeAccStatus(AccountDto accountDto , String accNumber) {
        Account account = this.accountRepo.findById(accNumber).orElseThrow(RuntimeException::new);
        account.setAccStatus(accountDto.isAccStatus());
        accountRepo.save(account);
        return accountDto;
    }*/

    private Account dtoToAccount(AccountDto accountDto){
        Account account = this.modelMapper.map(accountDto,Account.class);
        return  account;
    }
    private AccountDto accountToDto(Account account){
        AccountDto accountDto = this.modelMapper.map(account,AccountDto.class);
        return accountDto;
    }
}
