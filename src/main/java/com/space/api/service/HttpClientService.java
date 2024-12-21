package com.space.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.space.api.exception.ConditionsNotMetException;
import com.space.api.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
@Slf4j
public class HttpClientService {

    private String url;
    private final HttpClient client;
    private final long startTime;

    public HttpClientService(@Value("${api.url}") String url) {
        this.startTime = System.currentTimeMillis();
        this.client = HttpClient.newHttpClient();
        this.url = url;
    }

    public ResponseData fetchData() {
        log.info("начало подсчета времени для httpClient: {}", startTime);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            var astronauts = objectMapper.readValue(response.body(), Map.class);


            long endTime = System.currentTimeMillis();

            log.info("завершение подсчета времени для httpClient: {}", endTime);
            return new ResponseData("httpClient", endTime - startTime, astronauts.toString());
        } catch (Exception e) {
            throw new ConditionsNotMetException(e.getMessage());
        }
    }
}
