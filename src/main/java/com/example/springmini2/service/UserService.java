package com.example.springmini2.service;

import com.example.springmini2.model.User;

import java.util.List;

public interface UserService {
    List<User> findUsers();

    String login();
}
