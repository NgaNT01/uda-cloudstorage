package com.udacity.cloudstorage.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {

    private Integer id;
    private String title;
    private Integer userId;
    private String description;

    public Note() {}

    public Note(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    public Note(Integer noteId, String title, String description, Integer userId) {
        this.id = noteId;
        this.title = title;
        this.userId = userId;
        this.description = description;
    }
}
