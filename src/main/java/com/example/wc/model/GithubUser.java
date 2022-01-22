package com.example.wc.model;

import lombok.Data;


@Data
public class GithubUser {

    private long id;
    private String name;
    private String login;
    private String avatar_url;
    private String repos_url;


}
