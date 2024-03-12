package com.exam.controller;

import com.exam.exception.UserAlreadyRegisteredException;
import com.exam.exception.UserNotFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @PostMapping("/")
    public User createUser(@RequestBody User user){

        user.setProfile("default.png");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<UserRole> roles = new HashSet<>();
        Role role = new Role();
        role.setRoleId(45L);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        roles.add(userRole);
        return this.userService.createUser(user,roles);
    }
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username")String username){
        return this.userService.getUser(username);
    }
    //deleting the user by username
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId")Long userId){
        this.userService.deleteUser(userId);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<?> exceptionHandler(UserAlreadyRegisteredException userAlreadyRegisteredException){
        return new  ResponseEntity<>(userAlreadyRegisteredException,HttpStatus.BAD_REQUEST);
    }
}
