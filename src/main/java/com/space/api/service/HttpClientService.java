package com.space.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.space.api.exception.ConditionsNotMetException;
import com.space.api.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
@Slf4j
@Order(1)
public class HttpClientService implements ClientService{

    private String url;
    private final HttpClient client;

    public HttpClientService(@Value("${api.url}") String url) {
        this.client = HttpClient.newHttpClient();
        this.url = url;
    }

    @Override
    public ResponseData fetchData() {
        HttpRequest request = getHttpRequest();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            var astronauts = objectMapper.readValue(response.body(), Map.class);

            return new ResponseData("httpClient", 0L, astronauts.toString());
        } catch (Exception e) {
            log.error("Ошибка при выполнении HTTP-запроса: {}", e.getMessage(), e);
            throw new ConditionsNotMetException("Не удалось выполнить запрос: " + e.getMessage());
        }
    }

    private HttpRequest getHttpRequest() {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }
}
