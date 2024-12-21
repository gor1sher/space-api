package com.space.api.service;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor
public class WorkerPoolServiceTest {

    @Autowired
    private WorkerPoolService workerPoolService;

    @Test
    public void fastestClientTest() {
        workerPoolService.poolThread();
    }
}

