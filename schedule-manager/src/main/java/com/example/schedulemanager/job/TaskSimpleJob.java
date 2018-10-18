package com.example.springbootelasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/10 下午10:54
 */
public class TaskSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("shardingContext = " + shardingContext);
        System.out.println("my job task ** " + System.currentTimeMillis());
    }
}
