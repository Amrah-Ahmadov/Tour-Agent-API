package com.example.touragentapidemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "No such a user found !")
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("No such a user found !");
    }
}
