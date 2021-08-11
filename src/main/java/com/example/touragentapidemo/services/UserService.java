package com.example.touragentapidemo.services;

import com.example.touragentapidemo.dao.UserDao;
import com.example.touragentapidemo.dtos.UserRegistrationDTO;
import com.example.touragentapidemo.models.User;
import com.example.touragentapidemo.utils.DtoUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class UserService {
    UserDao dao;

    public UserService(UserDao dao){
        this.dao = dao;
    }

    public User register(UserRegistrationDTO userDto) {
        User user = DtoUtil.convertRegisterDtoToUser(userDto);
        user.setCreationDate(LocalDateTime.now());
        user.setActive(false);
        return dao.createUser(user);
    }
}
