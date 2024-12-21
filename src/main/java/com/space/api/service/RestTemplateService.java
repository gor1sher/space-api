package com.space.api.service;

import com.space.api.exception.ConditionsNotMetException;
import com.space.api.model.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestTemplateService {

    @Value("${api.url}")
    private String url;
    private final RestTemplate restTemplate;

    public ResponseData retrieveDataFromApi() {
        long startTime = System.currentTimeMillis();
        log.info("начало подсчета времени для restTemplate: {}", startTime);

        log.info("получение ответа о кол-ве людей в космосе");
        var response = restTemplate.getForObject(url, Map.class);

        if (response == null) {
            log.warn("ответ от API пустой: restTemplate");
            throw new ConditionsNotMetException("нет ответа от API");
        }

        List<Objects> astronauts = (List<Objects>) response.get("people");

        if (astronauts == null) {
            log.warn("полученный ответ от API пуст : restTemplate");
            throw new ConditionsNotMetException("полученный ответ от API пуст");
        }

        long endTime = System.currentTimeMillis();
        log.info("завершение подсчета времени для restTemplate: {}", endTime);

        return new ResponseData("restTemplate", endTime - startTime, astronauts.toString());
    }
}
