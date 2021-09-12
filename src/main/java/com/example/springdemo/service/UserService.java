package com.example.springdemo.service;

import com.example.springdemo.model.User;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Locale;

public interface UserService {

    List<User> findAllUsers();

    User findByEmail(String email);

    void addUser(User user, Locale locale);

    void sendVerificationEmail(User user, Locale locale) throws MessagingException;

    void verifyUser(String email, String token);
}
