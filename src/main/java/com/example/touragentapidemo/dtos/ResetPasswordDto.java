package com.example.touragentapidemo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResetPasswordDto {
    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;
}
