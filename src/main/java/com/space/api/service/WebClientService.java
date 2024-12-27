package com.space.api.service;

import com.space.api.exception.ConditionsNotMetException;
import com.space.api.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@Order(3)
public class WebClientService implements ClientService {

    private final WebClient webClient;

    public WebClientService(@Value("${api.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();

    }

    @Override
    public ResponseData fetchData() {
        var response = webClient.get()
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofSeconds(5))
                .block();

        if (response == null) {
            log.warn("полученный ответ от API пуст : webClient");
            throw new ConditionsNotMetException("полученный ответ от API пуст");
        }

        List<Objects> astronauts = (List<Objects>) response.get("people");

        if (astronauts == null) {
            log.warn("полученный ответ от API пуст : webClient");
            throw new ConditionsNotMetException("полученный ответ от API пуст");
        }

        return new ResponseData("webClient", 0L,  astronauts.toString());
    }
}
