package com.example.touragentapidemo.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class UserRegistrationDTO {
    private String companyName;
    private String name;
    private String surname;
    private String voen;
    private String email;
    private String password;
    private String repeatPassword;
}
