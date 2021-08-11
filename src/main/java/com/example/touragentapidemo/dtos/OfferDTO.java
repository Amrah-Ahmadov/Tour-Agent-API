package com.example.touragentapidemo.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class OfferDTO {
//    private long id;
    private String request;
    private String price;
    private String dateRange;
    private String additionalInfo;
}
