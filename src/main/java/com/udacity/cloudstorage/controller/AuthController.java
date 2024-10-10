package com.udacity.cloudstorage.controller;

import com.udacity.cloudstorage.domain.User;
import com.udacity.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginErrorView(Model model) {
        model.addAttribute("authenticationError", true);

        return "login";
    }

    @GetMapping("/logout-success")
    public String logoutView(Model model) {
        model.addAttribute("logoutSuccess", true);

        return "login";
    }

    @GetMapping("/signup")
    public String signupView() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;
        if (!userService.isUsernameAvailable(user.getUsername()))
            signupError = "The username '" + user.getUsername() + "' already exists.";

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);

            if (rowsAdded < 0)
                signupError = "There was an error signing you up. Please try again.";

        }

        if (signupError != null) {
            model.addAttribute("signupError", signupError);

            return "signup";
        }

        model.addAttribute("signupSuccess", true);
        return "login";
    }

}
