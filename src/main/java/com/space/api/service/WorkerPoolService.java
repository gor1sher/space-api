package com.space.api.service;

import com.space.api.exception.ConditionsNotMetException;
import com.space.api.model.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkerPoolService {

    private final HttpClientService httpClientService;
    private final RestTemplateService restTemplateService;
    private final WebClientService webClientService;

    @SneakyThrows
    public void poolThread(){
        log.info("запускаем клиенты в асинхронном виде");
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        var futureHttp = executorService.submit(() -> httpClientService.fetchData());
        var futureRestTemplate = executorService.submit(() -> restTemplateService.retrieveDataFromApi());
        var futureWebClient = executorService.submit(() -> webClientService.fetchData());

        List<Future<ResponseData>> list = List.of(futureHttp, futureRestTemplate, futureWebClient);
        List<ResponseData> responseDataList = new ArrayList<>();

        for(Future<ResponseData> data : list){
            ResponseData responseData = data.get();
            responseDataList.add(responseData);
            log.info(responseData.getClient() + " время выполнения: {}", responseData.getExecutionTime());
        }

        executorService.shutdown();

        ResponseData responseData = responseDataList.stream()
                .min(Comparator.comparingLong(ResponseData::getExecutionTime))
                .orElseThrow(() -> new ConditionsNotMetException("список пуст"));

        log.info("самый быстрый клиент " + responseData.getClient() + " его время выполнения: {}",
                responseData.getExecutionTime());

    }
}
