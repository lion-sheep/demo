package com.example.schedulemanager.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/10 下午10:54
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskSimpleJob implements SimpleJob {

    private String message;

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("shardingContext = " + shardingContext);
        System.out.println(message + " ** " + System.currentTimeMillis());
    }
}
