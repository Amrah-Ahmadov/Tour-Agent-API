package com.example.touragentapidemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Password you typed is incorrect !")
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Password you typed is incorrect !");
    }
}
