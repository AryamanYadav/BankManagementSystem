package com.Aryaman.BankManagementSystem.Repository;

import com.Aryaman.BankManagementSystem.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByUserName(String userName);


}
