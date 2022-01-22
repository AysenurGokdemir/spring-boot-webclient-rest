package com.example.wc.service;

import com.example.wc.model.GithubUser;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientRequest;

import java.time.Duration;


@Service
public class GithubUsersService {

    private final WebClient webClient;

    public GithubUsersService(WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<ResponseEntity<GithubUser[]>> getUsers(){
        return this.webClient
                .get()
                .uri("/users")
                .retrieve()
                .toEntity(GithubUser[].class)
                ;
                //.bodyToMono(GithubUser[].class)

    }
    public Mono<GithubUser[]> getUsersWithBodyToMono(){
        return this.webClient
                .get()
                .uri("/users")
                .retrieve()
                .bodyToMono(GithubUser[].class);

    }
    public Mono<GithubUser[]> getUsersWithTimeOut(){
        return this.webClient
                .get()
                .uri("/users").accept(MediaType.APPLICATION_JSON)
                .httpRequest(httpRequest -> {
                    HttpClientRequest reactorRequest = httpRequest.getNativeRequest();
                    reactorRequest.responseTimeout(Duration.ofSeconds(2));
                }).retrieve().bodyToMono(GithubUser[].class);


    }

    public Mono<GithubUser[]> getUsersWithErrorHandling(){
        return this.webClient
                .get()
                .uri("/users").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    System.out.println("4xx error");
                    return Mono.error(new RuntimeException("4xx"));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    System.out.println("5xx error");
                    return Mono.error(new RuntimeException("5xx"));
                }).bodyToMono(GithubUser[].class);


    }
}
