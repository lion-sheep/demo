package com.demo.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaoma
 * @desc
 * @date 2018/10/18 下午8:02
 */
@Configuration
public class ExecutorServiceConfig {

    @Bean(name = "executorService")
    public ExecutorService executorService() {
        ExecutorService pool = Executors.newCachedThreadPool();
        return pool;
    }
}

