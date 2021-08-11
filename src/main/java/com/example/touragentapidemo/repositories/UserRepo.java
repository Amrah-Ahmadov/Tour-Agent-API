package com.example.touragentapidemo.repositories;

import com.example.touragentapidemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer>{
    User getUserByEmail(String email);
}

