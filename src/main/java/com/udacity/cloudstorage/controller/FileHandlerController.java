package com.udacity.cloudstorage.controller;

import java.util.List;
import java.util.ArrayList;

import com.udacity.cloudstorage.domain.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import com.udacity.cloudstorage.domain.File;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.udacity.cloudstorage.services.FileStorageService;
import com.udacity.cloudstorage.services.UserManagementService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/files")
public class FileHandlerController {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(
            HttpServletResponse response,
            Authentication auth,
            @RequestParam(name = "file") MultipartFile uploadedFile,
            Model model
    ) {
        List<String> errorMessages = new ArrayList<>();

        if (uploadedFile.isEmpty()) {
            errorMessages.add(Constant.FILE_CANNOT_EMPTY);
        }

        model.addAttribute("success", true);
        Integer userId = userManagementService.retrieveUser(auth.getName()).getUserId();
        try {
            var newFile = new File(
                    uploadedFile.getOriginalFilename(),
                    uploadedFile.getSize(),
                    uploadedFile.getContentType(),
                    uploadedFile.getBytes(),
                    userId
            );

            if (fileStorageService.isFileExists(newFile)) {
                errorMessages.add(Constant.FILE_ALREADY_UPLOADED);
                model.addAttribute("errors", errorMessages);
                model.addAttribute("success", false);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "result";
            }

            fileStorageService.saveFile(newFile);

        } catch (Exception e) {
            errorMessages.add(Constant.FILE_PROCESSING_ERROR);
            model.addAttribute("success", false);
            model.addAttribute("errors", errorMessages);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        if (!errorMessages.isEmpty()) {
            model.addAttribute("errors", errorMessages);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @GetMapping(value = "/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(
            HttpServletResponse response,
            Authentication auth,
            @PathVariable Integer fileId
    ) {
        Integer userId = userManagementService.retrieveUser(auth.getName()).getUserId();
        var fileToDownload = fileStorageService.retrieveFile(new File(fileId, userId));

        if (fileToDownload != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileToDownload.getContentType()))
                    .header("Content-Disposition", "attachment; filename=\"" + fileToDownload.getName() + "\"")
                    .body(new ByteArrayResource(fileToDownload.getData()));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{fileId}")
    public String deleteFile(
            Authentication auth,
            HttpServletResponse response,
            @PathVariable Integer fileId,
            Model model
    ) {
        Integer userId = userManagementService.retrieveUser(auth.getName()).getUserId();
        List<String> errorMessages = new ArrayList<>();
        model.addAttribute("success", true);

        try {
            fileStorageService.deleteFile(new File(fileId, userId));
        } catch (Exception e) {
            errorMessages.add(Constant.UNABLE_TO_DELETE_FILE);
            model.addAttribute("errors", errorMessages);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "result";
    }
}
