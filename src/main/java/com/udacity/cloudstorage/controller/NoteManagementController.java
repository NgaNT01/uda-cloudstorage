package com.udacity.cloudstorage.controller;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.udacity.cloudstorage.domain.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import com.udacity.cloudstorage.domain.Note;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.udacity.cloudstorage.services.UserManagementService;
import com.udacity.cloudstorage.services.NoteManagementService;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/notes")
public class NoteManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private NoteManagementService noteManagementService;

    private List<String> validateNoteData(Map<String, String> noteData) {
        List<String> errorMessages = new ArrayList<>();

        if (noteData.get("noteTitle").isEmpty())
            errorMessages.add(Constant.TITLE_CANNOT_EMPTY);

        if (noteData.get("noteDescription").isEmpty())
            errorMessages.add(Constant.DESCRIPTION_CANNOT_EMPTY);

        return errorMessages;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createNote(
            HttpServletResponse response,
            Authentication auth,
            @RequestParam Map<String, String> noteData,
            Model model
    ) {
        var userId = userManagementService.retrieveUser(auth.getName()).getUserId();
        noteManagementService.saveOrUpdate(
                new Note(
                        null,
                        noteData.get("noteTitle"),
                        noteData.get("noteDescription"),
                        userId
                )
        );

        var errorMessages = validateNoteData(noteData);
        model.addAttribute("success", true);
        if (!errorMessages.isEmpty()) {
            model.addAttribute("errors", errorMessages);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @PutMapping(value = "/{noteId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateNote(
            HttpServletResponse response,
            Authentication auth,
            @RequestParam Map<String, String> noteData,
            Model model,
            @PathVariable String noteId) {
        var userId = userManagementService.retrieveUser(auth.getName()).getUserId();
        noteManagementService.saveOrUpdate(
                new Note(
                        Integer.parseInt(noteData.get("noteId")),
                        noteData.get("noteTitle"),
                        noteData.get("noteDescription"),
                        userId
                )
        );

        var errorMessages = validateNoteData(noteData);
        model.addAttribute("success", true);
        if (!errorMessages.isEmpty()) {
            model.addAttribute("errors", errorMessages);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @DeleteMapping(value = "/{noteId}")
    public String deleteNote(
            HttpServletResponse response,
            @PathVariable Integer noteId,
            Authentication auth,
            Model model
    ) {
        List<String> errorMessages = new ArrayList<>();
        model.addAttribute("success", true);
        try {
            var userId = userManagementService.retrieveUser(auth.getName()).getUserId();
            noteManagementService.deleteNote(new Note(noteId, userId));
        } catch (Exception e) {
            errorMessages.add(Constant.UNABLE_TO_DELETE_NOTE);
            model.addAttribute("errors", errorMessages);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "result";
    }
}
