package com.udacity.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import com.udacity.cloudstorage.services.FileService;
import com.udacity.cloudstorage.services.UserService;
import com.udacity.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import com.udacity.cloudstorage.services.CredentialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService users;

    @Autowired
    private NoteService notes;

    @Autowired
    private FileService files;

    @Autowired
    private CredentialService credentials;

    @GetMapping()
    public String homeView(Authentication authentication, Model model) {

        try {
            var UID = users.getUser(
                authentication.getName()
            ).getUserId()
             .toString();

            model.addAttribute("notes", notes.allBy(UID));
            model.addAttribute("files", files.allBy(UID));
            model.addAttribute("credentials", credentials.allBy(UID));

        } catch (Exception ignored) {
            return "redirect:/logout-success";
        }

        return "home";
    }

}
