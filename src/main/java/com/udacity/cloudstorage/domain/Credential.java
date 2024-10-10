package com.udacity.cloudstorage.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credential {

    private Integer id;
    private String url;
    private String key;
    private Integer userId;
    private String username;
    private String password;

    public Credential() {}

    public Credential(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    public Credential(String url, String username, String password, Integer userId) {
        this.url = url;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public Credential(Integer id, String url, String username, String password, Integer userId) {
        this.id = id;
        this.url = url;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public Credential(Integer id, String key, String url, String username, String password, Integer userId) {
        this.id = id;
        this.key = key;
        this.url = url;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
}
