package com.example.wc.controller;

import com.example.wc.model.GithubUser;
import com.example.wc.service.GithubUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GithubUsersController {
    @Autowired
    private GithubUsersService githubApiUserService;

    @GetMapping("/getAllUsers")
    Mono<ResponseEntity<GithubUser[]>> getAllUsers(){

        Mono<ResponseEntity<GithubUser[]>> users = githubApiUserService.getUsers();

        return users;
    }

    @GetMapping("/getAllUsersWithBodyToMono")
    Mono<GithubUser[]> getAllUsersWithBodyToMono(){

        Mono<GithubUser[]> users = githubApiUserService.getUsersWithBodyToMono();

        return users;
    }

    @GetMapping("/getUsersWithErrorHandling")
    Mono<GithubUser[]> getUsersWithErrorHandling(){

        Mono<GithubUser[]> users = githubApiUserService.getUsersWithErrorHandling();

        return users;
    }
    @GetMapping("/getUsersWithTimeOut")
    Mono<GithubUser[]> getUsersWithTimeOut(){

        Mono<GithubUser[]> users = githubApiUserService.getUsersWithTimeOut();

        return users;
    }
}
