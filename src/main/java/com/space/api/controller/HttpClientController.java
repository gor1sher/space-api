package com.space.api.controller;

import com.space.api.service.HttpClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/space-2")
@RequiredArgsConstructor
public class HttpClientController {

    private final HttpClientService httpClientService;

    @GetMapping
    public Object returnR(){
        return httpClientService.fetchData();
    }
}
