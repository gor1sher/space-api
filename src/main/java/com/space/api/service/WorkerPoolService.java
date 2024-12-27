package com.space.api.service;

import com.space.api.exception.ConditionsNotMetException;
import com.space.api.model.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j

public class WorkerPoolService {

    private final ClientService httpClientService;
    private final ClientService restTemplateService;
    private final ClientService webClientService;

    public WorkerPoolService(@Qualifier("httpTimeWatchedService") ClientService httpClientService,
                             @Qualifier("restTemplateTimeWatchedService") ClientService restTemplateService,
                             @Qualifier("webClientTimeWatchedService") ClientService webClientService) {

        this.httpClientService = httpClientService;
        this.restTemplateService = restTemplateService;
        this.webClientService = webClientService;
    }

    @SneakyThrows
    public String poolThread() {
        log.info("запускаем клиенты в асинхронном виде");
        List<ResponseData> responseDataList = new ArrayList<>();

        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {

            List<Future<ResponseData>> listClients = executorService
                    .invokeAll(List.of(httpClientService::fetchData, restTemplateService::fetchData, webClientService::fetchData));


            for (Future<ResponseData> data : listClients) {
                ResponseData responseData = data.get();
                responseDataList.add(responseData);
                log.info(responseData.getClient() + " время выполнения: {}", responseData.getExecutionTime());
            }
        }

        ResponseData responseData = responseDataList.stream()
                .filter(Objects::nonNull)
                .min(Comparator.comparingLong(ResponseData::getExecutionTime))
                .orElseThrow(() -> new ConditionsNotMetException("список пуст"));

        return ("самый быстрый клиент " + responseData.getClient() + " его время выполнения: " +
                responseData.getExecutionTime());

    }
}
