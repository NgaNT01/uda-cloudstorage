package com.udacity.cloudstorage.controller;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import com.udacity.cloudstorage.domain.Note;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.udacity.cloudstorage.services.UserService;
import com.udacity.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private UserService users;

    @Autowired
    private NoteService notes;

    public List<String> validate(Map<String, String> data) {
        List<String> errors = new ArrayList<String>();

        if (data.get("noteTitle").isEmpty())
            errors.add("Note title must not be empty.");

        if (data.get("noteDescription").isEmpty())
            errors.add("Note description must not be empty.");

        return errors;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createNoteView(
        HttpServletResponse response,
        Authentication authentication,
        @RequestParam Map<String, String> data,
        Model model
    ) {
        var UID = users.getUser(authentication.getName()).getUserId();
        notes.add(
            new Note(
                null,
                data.get("noteTitle"),
                data.get("noteDescription"),
                UID
            )
        );

        var errors = validate(data);
        model.addAttribute("success", true);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @PutMapping(value = "/{noteId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editNoteView(
        HttpServletResponse response,
        Authentication authentication,
        @RequestParam Map<String, String> data,
        Model model,
        @PathVariable String noteId) {
        var UID = users.getUser(authentication.getName()).getUserId();
        notes.add(
            new Note(
                Integer.parseInt(data.get("noteId")),
                data.get("noteTitle"),
                data.get("noteDescription"),
                UID
            )
        );

        var errors = validate(data);
        model.addAttribute("success", true);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @DeleteMapping(value = "/{noteId}")
    public String removeNoteView(
        HttpServletResponse response,
        @PathVariable Integer noteId,
        Authentication authentication,
        Model model
    ) {
        List<String> errors = new ArrayList<String>();
        model.addAttribute("success", true);
        try {
            var UID = users.getUser(authentication.getName()).getUserId();
            notes.remove(new Note(noteId, UID));

        } catch (Exception ignore) {
            errors.add("There was a server error. The note was not removed.");
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }

        return "result";
    }

}
