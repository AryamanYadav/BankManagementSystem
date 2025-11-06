package com.Aryaman.BankManagementSystem.Service;

import com.Aryaman.BankManagementSystem.Payloads.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto,Integer accountNumber);

    void deleteUser(Integer accountNumber);

    UserDto getById(Integer accountNumber);

    UserDto setRole(UserDto userDto , Integer userId , String role);

    void updateUserRole(int userId,String role);
}
