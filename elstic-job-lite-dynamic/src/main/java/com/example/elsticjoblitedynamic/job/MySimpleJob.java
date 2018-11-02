package com.example.elsticjoblitedynamic.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/16 上午1:34
 */
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("----------------simpleJob----------------");
        System.out.println(System.currentTimeMillis());
        System.out.println("JobName:" + shardingContext.getJobName());
        System.out.println("JobParameter:" + shardingContext.getJobParameter());
        System.out.println("ShardingItem:" + shardingContext.getShardingItem());
        System.out.println("ShardingParameter:" + shardingContext.getShardingParameter());
        System.out.println("ShardingTotalCount:" + shardingContext.getShardingTotalCount());
        System.out.println("TaskId:" + shardingContext.getTaskId());
        System.out.println("----------------simpleJob----------------");
    }
}
