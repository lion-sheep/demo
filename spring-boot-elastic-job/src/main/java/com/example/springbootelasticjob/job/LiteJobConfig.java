package com.example.springbootelasticjob.job;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import org.springframework.stereotype.Component;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/10 下午11:44
 */
@Component
public class LiteJobConfig {

    public LiteJobConfiguration liteJobConfiguration(Class<? extends SimpleJob> jobClass, String cron, int shardingTotalCount) {
        /**
         * 创建简单作业配置构建器.
         *
         * @param jobName 作业名称
         * @param cron 作业启动时间的cron表达式
         * @param shardingTotalCount 作业分片总数
         * @return 简单作业配置构建器
         */
        String jobName = jobClass.getName();
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount).build();
        String jobClazz = jobClass.getCanonicalName();
        /*
         * 设置本地配置是否可覆盖注册中心配置.
         *
         * <p>
         * 如果可覆盖, 每次启动作业都以本地配置为准.
         * </p>
         *
         * @param overwrite 本地配置是否可覆盖注册中心配置
         *
         * @return 作业配置构建器

          public LiteJobConfiguration.Builder overwrite(final boolean overwrite) {
             this.overwrite = overwrite;
             return this;
         }
         */
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(jobCoreConfiguration, jobClazz)).overwrite(true).build();
    }
}
