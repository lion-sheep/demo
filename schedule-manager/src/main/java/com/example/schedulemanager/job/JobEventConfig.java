package com.example.schedulemanager.job;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/13 下午5:14
 */
@Configuration
public class JobEventConfig {
    @Autowired
    private DataSource dataSource;

    @Bean(name = "jobEventConfiguration")
    public JobEventConfiguration jobEventConfiguration() {
        return new JobEventRdbConfiguration(dataSource);
    }
}
