package com.example.touragentapidemo.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class RequestDto {
    private String requestId;
    private String requestContext;
}
