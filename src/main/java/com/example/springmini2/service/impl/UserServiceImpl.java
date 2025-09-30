package com.example.springmini2.service.impl;

import com.example.springmini2.model.User;
import com.example.springmini2.repository.UserRepository;
import com.example.springmini2.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @Override
    public String login() {
        return "";
    }
}
