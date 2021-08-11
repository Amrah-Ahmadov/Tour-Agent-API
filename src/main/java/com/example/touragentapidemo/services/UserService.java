package com.example.touragentapidemo.services;

import com.example.touragentapidemo.dao.UserDao;
import com.example.touragentapidemo.dtos.UserRegistrationDTO;
import com.example.touragentapidemo.models.User;
import com.example.touragentapidemo.repositories.UserRepo;
import com.example.touragentapidemo.utils.DtoUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class UserService {
    private final UserRepo userRepo;
    UserDao dao;


    public UserService(UserRepo userRepo, UserDao dao){
        this.userRepo = userRepo;
        this.dao = dao;
    }
    public User getUserByEmail(String email){
        return userRepo.getUserByEmail(email);
    }
    public void saveUser(User user){
        userRepo.saveAndFlush(user);
    }

    public User register(UserRegistrationDTO userDto) {
        User user = DtoUtil.convertRegisterDtoToUser(userDto);
        user.setCreationDate(LocalDateTime.now());
        user.setActive(false);
        return dao.createUser(user);
    }
}
