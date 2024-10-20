package com.udacity.cloudstorage.services;

import java.util.List;
import com.udacity.cloudstorage.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.cloudstorage.mapper.FileMapper;

@Service
public class FileStorageService {

    @Autowired
    private FileMapper fileMapper;

    public File retrieveFile(File file) {
        return fileMapper.getFile(file);
    }

    public List<File> getAllFilesForUser(String userId) {
        return fileMapper.getAllFilesByUserId(userId);
    }

    public void deleteFile(File file) {
        fileMapper.removeFile(file);
    }

    public boolean isFileExists(File file) {
        return fileMapper.findByName(file) != null;
    }

    public void saveFile(File file) {
        fileMapper.addFile(file);
    }
}
