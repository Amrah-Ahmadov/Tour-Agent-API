package com.example.touragentapidemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "This request is expired!")
public class RequestExpiredException extends Exception{
    public RequestExpiredException() {
        super("This request is expired!");
    }
}
