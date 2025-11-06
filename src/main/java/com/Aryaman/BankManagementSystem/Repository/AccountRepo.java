package com.Aryaman.BankManagementSystem.Repository;

import com.Aryaman.BankManagementSystem.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,String> {
    boolean existsByAccountNumber(String accountNumber);
}
