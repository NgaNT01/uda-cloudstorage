package com.udacity.cloudstorage.services;

import java.util.List;
import com.udacity.cloudstorage.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.cloudstorage.mapper.NoteMapper;

@Service
public class NoteManagementService {

    @Autowired
    private NoteMapper noteMapper;

    public List<Note> getAllNotesByUserId(String userId) {
        return noteMapper.allFrom(userId);
    }

    public void deleteNote(Note note) {
        noteMapper.delete(note);
    }

    public void saveOrUpdate(Note note) {
        if (note.getId() == null) {
            noteMapper.insert(note);
        } else {
            noteMapper.update(note);
        }
    }
}
