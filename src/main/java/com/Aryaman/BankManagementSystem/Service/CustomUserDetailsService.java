package com.Aryaman.BankManagementSystem.Service;

import com.Aryaman.BankManagementSystem.Entities.User;
import com.Aryaman.BankManagementSystem.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Attempting login for username: " + username);

        return userRepo.findByUserName(username)
                .map(user -> {
                    System.out.println("✅ User found: " + user.getUsername());

                    System.out.println("✅ User found in DB: " + user.getUsername());
                    System.out.println("🧩 Password in DB (encoded): " + user.getPassword());
                    System.out.println("🧩 Role in DB: " + user.getRole());


                    return user;
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    }

}

