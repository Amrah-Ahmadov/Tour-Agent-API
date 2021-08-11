package com.example.touragentapidemo.dtos;

import com.example.touragentapidemo.models.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    public UserDto(User user){
        this.fullName = user.getFullName();
        this.id = user.getId();
    }
    private int id;
    private String fullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
