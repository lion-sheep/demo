package com.example.schedulemanager.job;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import org.springframework.stereotype.Component;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/13 下午5:19
 */
@Component
public class LiteJobConfig {

    /**
     * @param jobClass           作业类
     * @param cron               作业启动时间的cron表达式
     * @param shardingTotalCount 作业分片总数
     * @author xiaoma
     * @date 2018/9/13 下午5:20
     */

    public LiteJobConfiguration liteJobConfiguration(Class<? extends SimpleJob> jobClass, String cron, int shardingTotalCount) {
        String jobName = jobClass.getName();//作业名称
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount).build();
        String jobClazz = jobClass.getCanonicalName();
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(jobCoreConfiguration, jobClazz)).overwrite(true).build();
    }
}
