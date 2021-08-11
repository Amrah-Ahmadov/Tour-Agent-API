package com.example.touragentapidemo.utils;

import com.example.touragentapidemo.dtos.UserRegistrationDTO;
import com.example.touragentapidemo.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DtoUtil {

    public static User convertRegisterDtoToUser(UserRegistrationDTO userRegisterDto){
        return User.builder().companyName(userRegisterDto.getCompanyName())
                .name(userRegisterDto.getName())
                .surname(userRegisterDto.getSurname())
                .voen(userRegisterDto.getVoen())
                .email(userRegisterDto.getEmail()).surname(userRegisterDto.getSurname())
                .password( new BCryptPasswordEncoder().encode(userRegisterDto.getPassword()))
                .build();
    }
}
