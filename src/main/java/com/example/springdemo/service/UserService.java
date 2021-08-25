package com.example.springdemo.service;

import com.example.springdemo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    void addUser(User user);

}
