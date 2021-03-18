package com.api.uber.services;

import com.api.uber.model.User;
import com.api.uber.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User saveUser(User u){
        u.setPassword(BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(10)));
        return repository.save(u);
    }
}

