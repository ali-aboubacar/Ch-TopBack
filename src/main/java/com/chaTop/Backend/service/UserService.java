package com.chaTop.Backend.service;

import com.chaTop.Backend.model.User;
import com.chaTop.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public Optional<User> getRentalById(long id) {
        return userRepository.findById(id);
    }
}
