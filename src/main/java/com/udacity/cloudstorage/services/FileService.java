package com.udacity.cloudstorage.services;

import java.util.List;
import com.udacity.cloudstorage.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.cloudstorage.mapper.FileMapper;

@Service
public class FileService {

    @Autowired
    private FileMapper files;

    public File get(File file) {
        return files.get(file);
    }

    public List<File> allBy(String UID) {
        return files.allFrom(UID);
    }

    public void remove(File file) {
        files.delete(file);
    }

    public boolean exists(File file) {
        return files.find(file) != null;
    }

    public void store(File file) {
        files.insert(file);
    }

}
