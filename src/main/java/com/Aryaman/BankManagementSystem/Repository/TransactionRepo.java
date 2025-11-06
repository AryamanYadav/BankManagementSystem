package com.Aryaman.BankManagementSystem.Repository;

import com.Aryaman.BankManagementSystem.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction,Integer> {
}
