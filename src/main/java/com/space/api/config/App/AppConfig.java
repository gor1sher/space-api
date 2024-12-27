package com.space.api.config.App;

import com.space.api.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ClientService httpTimeWatchedService(HttpClientService httpClientService){
        return new TimeWatcherClientService(httpClientService);
    }

    @Bean
    public ClientService restTemplateTimeWatchedService(RestTemplateService restTemplateService){
        return new TimeWatcherClientService(restTemplateService);
    }

    @Bean
    public ClientService webClientTimeWatchedService(WebClientService webClientService){
        return new TimeWatcherClientService(webClientService);
    }
}
