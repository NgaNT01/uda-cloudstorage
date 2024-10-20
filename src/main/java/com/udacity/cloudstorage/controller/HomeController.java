package com.udacity.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import com.udacity.cloudstorage.services.FileStorageService;
import com.udacity.cloudstorage.services.UserManagementService;
import com.udacity.cloudstorage.services.NoteManagementService;
import org.springframework.security.core.Authentication;
import com.udacity.cloudstorage.services.CredentialManagementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserManagementService users;

    @Autowired
    private NoteManagementService notes;

    @Autowired
    private FileStorageService files;

    @Autowired
    private CredentialManagementService credentials;

    @GetMapping()
    public String homeView(Authentication authentication, Model model) {

        try {
            var UID = users.retrieveUser(
                authentication.getName()
            ).getUserId()
             .toString();

            model.addAttribute("notes", notes.getAllNotesByUserId(UID));
            model.addAttribute("files", files.getAllFilesForUser(UID));
            model.addAttribute("credentials", credentials.getAllCredentialsByUserId(UID));

        } catch (Exception ignored) {
            return "redirect:/logout-success";
        }

        return "home";
    }

}
