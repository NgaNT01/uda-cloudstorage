package com.udacity.cloudstorage.controller;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.udacity.cloudstorage.domain.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import com.udacity.cloudstorage.domain.Credential;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.udacity.cloudstorage.services.UserManagementService;
import org.springframework.security.core.Authentication;
import com.udacity.cloudstorage.services.CredentialManagementService;

@Controller
@RequestMapping("/credentials")
public class CredentialManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private CredentialManagementService credentialManagementService;

    private List<String> checkForErrors(Map<String, String> inputData) {
        List<String> errorMessages = new ArrayList<>();

        if (inputData.get("url").isEmpty())
            errorMessages.add(Constant.URL_CANNOT_EMPTY);

        if (inputData.get("username").isEmpty())
            errorMessages.add(Constant.USERNAME_CANNOT_EMPTY);

        if (inputData.get("password").isEmpty())
            errorMessages.add(Constant.PASSWORD_CANNOT_EMPTY);

        return errorMessages;
    }

    @ResponseBody
    @GetMapping("{credentialId}")
    public ResponseEntity<String> getDecryptedCredential(@PathVariable Integer credentialId, Authentication auth) {
        try {
            Integer userId = userManagementService.retrieveUser(auth.getName()).getUserId();
            String decryptedData = credentialManagementService.retrieveDecryptedPassword(new Credential(credentialId, userId));
            return new ResponseEntity<>(decryptedData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addCredential(
            HttpServletResponse response,
            Authentication auth,
            @RequestParam Map<String, String> inputData,
            Model model
    ) {
        Integer userId = userManagementService.retrieveUser(auth.getName()).getUserId();
        credentialManagementService.saveCredential(
                new Credential(
                        inputData.get("url"),
                        inputData.get("username"),
                        inputData.get("password"),
                        userId
                )
        );

        List<String> errorMessages = checkForErrors(inputData);
        model.addAttribute("success", true);
        if (!errorMessages.isEmpty()) {
            model.addAttribute("errors", errorMessages);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @PutMapping(value = "{credentialId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateCredential(
            HttpServletResponse response,
            Authentication auth,
            @RequestParam Map<String, String> inputData,
            Model model,
            @PathVariable String credentialId) {
        Integer userId = userManagementService.retrieveUser(auth.getName()).getUserId();
        credentialManagementService.saveCredential(
                new Credential(
                        Integer.parseInt(inputData.get("credentialId")),
                        inputData.get("url"),
                        inputData.get("username"),
                        inputData.get("password"),
                        userId
                )
        );

        List<String> errorMessages = checkForErrors(inputData);
        model.addAttribute("success", true);
        if (!errorMessages.isEmpty()) {
            model.addAttribute("errors", errorMessages);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @DeleteMapping(value = "{credentialId}")
    public String deleteCredential(
            HttpServletResponse response,
            @PathVariable Integer credentialId,
            Authentication auth,
            Model model
    ) {
        List<String> errorMessages = new ArrayList<>();
        model.addAttribute("success", true);
        try {
            Integer userId = userManagementService.retrieveUser(auth.getName()).getUserId();
            credentialManagementService.deleteCredential(new Credential(credentialId, userId));
        } catch (Exception e) {
            errorMessages.add(Constant.UNABLE_TO_DELETE_CREDENTIAL);
            model.addAttribute("errors", errorMessages);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "result";
    }
}
