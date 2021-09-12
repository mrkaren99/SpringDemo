package com.example.springdemo.controller;

import com.example.springdemo.model.User;
import com.example.springdemo.service.MailService;
import com.example.springdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/users")
    public String users(ModelMap modelMap) {
        List<User> all = userService.findAllUsers();
        modelMap.addAttribute("users", all);
        return "users";
    }

    @GetMapping("/users/add")
    public String addUser() {
        return "addUser";
    }

    @PostMapping("/users/add")
    public String addUserPost(@ModelAttribute User user, Locale locale) {
        User byEmail = userService.findByEmail(user.getEmail());
        if (byEmail != null) {
            return "redirect:/users";
        }
        userService.addUser(user, locale);
        return "redirect:/users";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("email") String email,
                              @RequestParam("token") String token) {
        userService.verifyUser(email, token);
        return "emailVerifySuccess";
    }


}
