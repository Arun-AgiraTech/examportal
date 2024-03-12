package com.exam.exception;

public class UserAlreadyRegisteredException extends Exception{
    public UserAlreadyRegisteredException (String errMessage , Throwable cause){
            super(errMessage,cause);
    }
    public UserAlreadyRegisteredException(){
        super("User with username is already there !! try with another name");
    }
}
