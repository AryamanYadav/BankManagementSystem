package com.Aryaman.BankManagementSystem.Controller;

import com.Aryaman.BankManagementSystem.Entities.Account;
import com.Aryaman.BankManagementSystem.Entities.User;
import com.Aryaman.BankManagementSystem.Exceptions.ResourceNotFoundException;
import com.Aryaman.BankManagementSystem.Payloads.AccountDto;
import com.Aryaman.BankManagementSystem.Payloads.ApiResponse;
import com.Aryaman.BankManagementSystem.Payloads.UserDto;
import com.Aryaman.BankManagementSystem.Repository.UserRepo;
import com.Aryaman.BankManagementSystem.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")

public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepo userRepo;


    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto = this.userService.createUser(userDto);
        System.out.println("Register API reached");

        return new ResponseEntity<UserDto>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable ("userId") Integer userId){
        UserDto updateUserDto = this.userService.updateUser(userDto,userId);
        return ResponseEntity.ok(updateUserDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId){
         this.userService.deleteUser(userId);
       return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(this.userService.getById(userId));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/role/{userId}")
    public ResponseEntity<ApiResponse> updateUserRole(@PathVariable int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(RuntimeException::new);
        String userRole=user.getRole();
        if(userRole.equals("ADMIN")){
            user.setRole("USER");
        }
        if(userRole.equals("USER")){
            user.setRole("ADMIN");
        }
        userRepo.save(user);
        return ResponseEntity.ok(new ApiResponse("User role updated successfully", true));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/changeStatus/{userId}")
    public ResponseEntity<ApiResponse> setUserStatus(@PathVariable int userId){
        User user=this.userRepo.findById(userId).orElseThrow(RuntimeException::new);
        boolean userStatus=user.isEnabled();
        if(userStatus){
            user.setEnabled(false);
        }
        if(!userStatus){
            user.setEnabled(true);
        }
        userRepo.save(user);


        return ResponseEntity.ok(new ApiResponse("Status Updated Successfully!",true));
    }

}
