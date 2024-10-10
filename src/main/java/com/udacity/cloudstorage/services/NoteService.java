package com.udacity.cloudstorage.services;

import java.util.List;
import com.udacity.cloudstorage.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.cloudstorage.mapper.NoteMapper;

@Service
public class NoteService {

    @Autowired
    private NoteMapper notes;

    public List<Note> allBy(String UID) {
        return notes.allFrom(UID);
    }

    public void remove(Note note) {
        notes.delete(note);
    }

    public void add(Note note) {
        if (note.getId() == null) {
            notes.insert(note);
            return;
        }

        notes.update(note);
    }

}
