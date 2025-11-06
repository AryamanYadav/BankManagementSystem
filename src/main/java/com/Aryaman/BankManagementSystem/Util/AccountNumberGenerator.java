package com.Aryaman.BankManagementSystem.Util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
@Component
public class AccountNumberGenerator {

    private static final SecureRandom random = new SecureRandom();

    public String generateAccountNumber(){
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<16;i++){
         sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}
