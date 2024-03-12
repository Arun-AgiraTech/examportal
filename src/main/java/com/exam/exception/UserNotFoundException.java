package com.exam.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("User Not Found !! Try proper Credentials");
    }
    public UserNotFoundException(String errMessage, Throwable cause) {
        super(errMessage, cause);
    }
}
