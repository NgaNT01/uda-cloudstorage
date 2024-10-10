package com.udacity.cloudstorage.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class File {

    private Integer id;
    private String name;
    private byte[] data;
    private long size;
    private Integer userId;
    private String contentType;

    public File() {}

    public File(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    public File(String name, long size, String contentType, byte[] data, Integer userId) {
        this.name = name;
        this.data = data;
        this.size = size;
        this.userId = userId;
        this.contentType = contentType;
    }
}
