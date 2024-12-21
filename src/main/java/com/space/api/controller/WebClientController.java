package com.space.api.controller;

import com.space.api.service.WebClientService;
import com.space.api.service.WorkerPoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/space-1")
@RequiredArgsConstructor
public class WebClientController {

    private final WorkerPoolService workerPoolService;
    private final WebClientService webClientService;

    @GetMapping
    public Object returnR() {
        workerPoolService.poolThread();
        return webClientService.fetchData();
    }
}
