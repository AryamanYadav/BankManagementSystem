package com.Aryaman.BankManagementSystem.ServiceImpl;


import com.Aryaman.BankManagementSystem.Entities.User;
import com.Aryaman.BankManagementSystem.Exceptions.GlobalExceptionalHandler;
import com.Aryaman.BankManagementSystem.Exceptions.ResourceNotFoundException;
import com.Aryaman.BankManagementSystem.Payloads.UserDto;
import com.Aryaman.BankManagementSystem.Repository.UserRepo;
import com.Aryaman.BankManagementSystem.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRole("USER");
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer accountNumber) {
        User user = this.userRepo.findById(accountNumber).orElseThrow(()-> new ResourceNotFoundException("User","AccountNumber",accountNumber));
        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserAddress(userDto.getUserAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        if(user.getRole()!=null || !user.getRole().isEmpty()){
            user.setRole(userDto.getRole());
        }
        else if (user.getRole() == null) {
            user.setRole("USER");
        }
        return userDto1;
    }

    @Override
    public void deleteUser(Integer accountNumber) {

        User deleteUser = this.userRepo.findById(accountNumber).orElseThrow(()-> new ResourceNotFoundException("User","Account Number",accountNumber));
        this.userRepo.delete(deleteUser);
    }

    @Override
    public UserDto getById(Integer accountNumber) {
        User user = this.userRepo.findById(accountNumber).orElseThrow(()-> new ResourceNotFoundException("User","AccountNumber",accountNumber));
        return this.userToDto(user);
    }

    @Override
    public UserDto setRole(UserDto userDto , Integer userId ,String role) {
        User user = this.userRepo.findById(userId).orElseThrow(()->  new ResourceNotFoundException("User","User Id",userId));
        if(user.getRole().equals("ADMIN") || user.getRole().equals("admin")) {
            System.out.println("Admin cannot change a role of a admin");

        }
        else {
            user.setRole(userDto.getRole());
            User updatedUser = this.userRepo.save(user);
            UserDto userDto1 = this.userToDto(updatedUser);
            return userDto1;
        }
        return null;
    }

    private User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto,User.class);
        return  user;
    }
    private UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }
    public void updateUserRole(int userId, String role) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        user.setRole(role);
        userRepo.save(user);
    }



}
