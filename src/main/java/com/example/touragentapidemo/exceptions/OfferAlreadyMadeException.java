package com.example.touragentapidemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Offer already made to current request!")
public class OfferAlreadyMadeException extends RuntimeException {
    public OfferAlreadyMadeException() {
        super("Offer already made to current request!");
    }
}
