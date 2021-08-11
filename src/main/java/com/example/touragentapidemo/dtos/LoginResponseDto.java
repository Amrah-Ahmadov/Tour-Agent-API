package com.example.touragentapidemo.dtos;

import com.example.touragentapidemo.models.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    public LoginResponseDto(User user, String token) {
        this.token = token;
        this.user = new UserDto(user);
    }

    private String token;
    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}