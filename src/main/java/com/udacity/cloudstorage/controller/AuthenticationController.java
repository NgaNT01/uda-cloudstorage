package com.udacity.cloudstorage.controller;

import com.udacity.cloudstorage.domain.Constant;
import com.udacity.cloudstorage.domain.User;
import com.udacity.cloudstorage.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthenticationController {

    @Autowired
    private UserManagementService userManagementService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/login-error")
    public String showLoginError(Model model) {
        model.addAttribute("authenticationError", true);
        return "login";
    }

    @GetMapping("/logout-success")
    public String showLogoutPage(Model model) {
        model.addAttribute("logoutSuccess", true);
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView registerUser(@ModelAttribute User user, RedirectAttributes attributes) {
        String errorMessage = null;

        if (!userManagementService.isUsernameAvailable(user.getUsername())) {
            errorMessage = "The username '" + user.getUsername() + "' is already taken.";
        }

        if (errorMessage == null) {
            int result = userManagementService.registerUser(user);
            if (result < 0) {
                errorMessage = Constant.ERROR_SIGNUP;
            }
        }

        if (errorMessage != null) {
            attributes.addFlashAttribute("signupError", errorMessage);
            return new RedirectView("/signup");
        }

        attributes.addFlashAttribute("signupSuccess", true);
        return new RedirectView("login");
    }
}
