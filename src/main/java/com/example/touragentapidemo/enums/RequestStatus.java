package com.example.touragentapidemo.enums;

public enum RequestStatus {
    NEW_REQUEST("New request"),
    OFFER_MADE("Offer made"),
    ACCEPTED("Accepted"),
    EXPIRED("Expired");

    private final String name;
    RequestStatus(String name) {
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
}
