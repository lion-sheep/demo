package com.example.elsticjoblitedynamic.job;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/16 上午1:38
 */
@Component
public class ElasticJob {
    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;
    @Autowired
    private JobEventConfiguration jobEventConfiguration;

    /**
     * 动态添加
     */
    public void addSimpleJobScheduler(
            Class<? extends SimpleJob> jobClass,
            String cron,
            int shardingTotalCount,
            String shardingItemParameters,
            String jobParameter) {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters).jobParameter(jobParameter).build();
        JobTypeConfiguration jobTypeConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, jobClass.getCanonicalName());
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(jobTypeConfiguration).build();
        JobScheduler jobScheduler = new JobScheduler(zookeeperRegistryCenter, liteJobConfiguration, jobEventConfiguration);
        jobScheduler.init();
    }

    /**
     * 动态添加
     */
    public void addDataFlowJobScheduler(
            Class<? extends DataflowJob> jobClass,
            String cron,
            int shardingTotalCount,
            String shardingItemParameters,
            String jobParameter) {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters).jobParameter(jobParameter).build();
        JobTypeConfiguration jobTypeConfiguration = new DataflowJobConfiguration(jobCoreConfiguration, jobClass.getCanonicalName(), true);
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(jobTypeConfiguration).build();
        JobScheduler jobScheduler = new JobScheduler(zookeeperRegistryCenter, liteJobConfiguration, jobEventConfiguration);
        jobScheduler.init();
    }
}
