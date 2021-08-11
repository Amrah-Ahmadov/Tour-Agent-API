package com.example.touragentapidemo.dtos;

import com.example.touragentapidemo.models.UsersRequests;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class OfferGetDto {
    private long id;
//    private String request;
    private String price;
    private String dateRange;
    private String additionalInfo;
    private UsersRequestsDto usersRequests;
}
