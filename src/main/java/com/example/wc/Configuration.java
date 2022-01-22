package com.example.wc;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.logging.Logger;

@Component
public class Configuration {
    Logger logger = Logger.getLogger(Configuration.class.getName());
    private static String BASE_URL = "https://api.github.com";
    @Bean
    public WebClient webClient() {

        return WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(logRequest())
                .build();
    }
        private ExchangeFilterFunction logRequest() {
            return (clientRequest, next) -> {
                logger.info("Request:" + clientRequest.method() +" : "+ clientRequest.url());
                clientRequest.headers()
                        .forEach((name, values) -> values.forEach(value -> logger.info( name +"="+ value)));
                return next.exchange(clientRequest);
            };
        }
}
