package com.example.touragentapidemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "It is not working hour that's why you can't make an offer!")
public class NotWorkingHourException extends RuntimeException{
    public NotWorkingHourException() {
        super("It is not working hour that's why you can't make an offer!");
    }
}
