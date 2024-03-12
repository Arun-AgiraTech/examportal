package com.exam.controller;

import com.exam.config.JwtUtils;
import com.exam.exception.UserNotFoundException;
import com.exam.model.JWTRequest;
import com.exam.model.JWTResponse;
import com.exam.model.User;
import com.exam.service.impl.UserDetailsServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;


    //generate token
    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JWTRequest jwtRequest){
        try {
            authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new UserNotFoundException();
        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JWTResponse(token));
    }


    @SneakyThrows
    private void authenticate(String username,String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException e){
            throw new Exception("USER DISABLED"+e.getMessage());
        }catch (BadCredentialsException e){
            throw new Exception("Invalid Credentials "+e.getMessage());
        }
    }

    //returning the details of the user having jwt token
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){
        return (User)this.userDetailsService.loadUserByUsername(principal.getName());
    }
}
