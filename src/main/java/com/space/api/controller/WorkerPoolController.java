package com.space.api.controller;

import com.space.api.service.WorkerPoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/space")
@RequiredArgsConstructor
public class WorkerPoolController {

    private final WorkerPoolService workerPoolService;

    @GetMapping
    public Object returnR() {
        return workerPoolService.poolThread();
    }
}
