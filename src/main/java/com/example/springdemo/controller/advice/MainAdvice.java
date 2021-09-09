package com.example.springdemo.controller.advice;

import com.example.springdemo.model.User;
import com.example.springdemo.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MainAdvice {

//    @ModelAttribute
//    public void currentUser(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
//        modelMap.addAttribute("currentUser", currentUser.getUser());
//    }

    @ModelAttribute("currentUser")
    public User currentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return new User();
        }
        return currentUser.getUser();
    }

}
