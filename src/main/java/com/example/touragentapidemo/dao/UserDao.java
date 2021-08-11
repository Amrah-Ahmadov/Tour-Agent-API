package com.example.touragentapidemo.dao;

import com.example.touragentapidemo.models.User;
import com.example.touragentapidemo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class UserDao {
    @Autowired
    UserRepo userRepo;

    public User createUser(User user) {
        try {
            return userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw e;
        }
    }
}