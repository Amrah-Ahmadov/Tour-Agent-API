package com.example.touragentapidemo.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class UsersRequestsDto {
    private Long id;
    private RequestDto requestDto;
    private String requestStatus;
    private String clientContactInfo;
}
