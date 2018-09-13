package com.example.springbootelasticjob.job;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/11 下午6:12
 */
@Configuration
@ConfigurationProperties(prefix = "job")
public class JobSchedulerConfig {
    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;
    @Autowired
    private JobEventConfiguration jobEventConfiguration;
    @Autowired
    private LiteJobConfig liteJobConfig;

    private String cron;

    private int shardingTotalCount;

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public int getShardingTotalCount() {
        return shardingTotalCount;
    }

    public void setShardingTotalCount(int shardingTotalCount) {
        this.shardingTotalCount = shardingTotalCount;
    }

    @Bean(initMethod = "init", name = "jobScheduler")
    public JobScheduler jobScheduler() {
        SimpleJob simpleJob = new TaskSimpleJob();
        LiteJobConfiguration liteJobConfiguration = liteJobConfig.liteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount);
        return new SpringJobScheduler(simpleJob, zookeeperRegistryCenter, liteJobConfiguration, jobEventConfiguration);
    }

}
