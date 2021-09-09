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

import java.util.List;

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
    public String addUserPost(@ModelAttribute User user){

        userService.addUser(user);
        mailService.send(user.getEmail(), "Welcome",
                "Dear "+ user.getName() + ", You have successfully registered to our web site!" );
        return "redirect:/users";
    }


}
