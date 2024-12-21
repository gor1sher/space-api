package com.space.api.service;

import com.space.api.exception.ConditionsNotMetException;
import com.space.api.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class WebClientService {

    private final WebClient webClient;
    private final long startTime;

    public WebClientService(@Value("${api.url}") String url) {
        this.startTime = System.currentTimeMillis();
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();

    }

    public ResponseData fetchData() {
        log.info("начало подсчета времени для webClient: {}", startTime);

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

        long endTime = System.currentTimeMillis();

        log.info("завершение подсчета времени для webClient: {}", endTime);
        return new ResponseData("webClient", endTime - startTime, astronauts.toString());
    }
}
