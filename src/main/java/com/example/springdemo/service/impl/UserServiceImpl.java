package com.example.springdemo.service.impl;

import com.example.springdemo.model.User;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.MailService;
import com.example.springdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Value("${site.url}")
    private String siteUrl;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void addUser(User user, Locale locale) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        try {
            sendVerificationEmail(user, locale);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    public void sendVerificationEmail(User user, Locale locale) throws MessagingException {
        UUID uuid = UUID.randomUUID();
        user.setToken(uuid);
        userRepository.save(user);
        String verifyUrl = siteUrl + "/verifyEmail?email=" + user.getEmail() + "&token=" + uuid;
//        mailService.send(user.getEmail(), "Verify your account",
//                "Hi " + user.getName() + ", \n" +
//                        String.format("Please verify your account by clicking on <a href='%s'> Link </a> ", verifyUrl));
        mailService.sendHtmlEmail(user.getEmail(),
                "Verify Your account",user, verifyUrl, "verifyTemplate",locale);

    }

    @Override
    public void verifyUser(String email, String token) {
        User byEmail = findByEmail(email);
        if (byEmail != null && byEmail.getToken().equals(UUID.fromString(token))) {
            byEmail.setEmailVerified(true);
            byEmail.setToken(null);
            userRepository.save(byEmail);
        }
    }
}
