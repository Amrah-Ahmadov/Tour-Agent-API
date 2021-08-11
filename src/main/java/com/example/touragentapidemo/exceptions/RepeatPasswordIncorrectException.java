package com.example.touragentapidemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Repeat password is incorrect")
public class RepeatPasswordIncorrectException extends RuntimeException{
    public RepeatPasswordIncorrectException() {
        super("Repeat password is incorrect");
    }
}
