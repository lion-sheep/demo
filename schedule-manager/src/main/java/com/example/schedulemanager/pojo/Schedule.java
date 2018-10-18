package com.example.springbootelasticjob.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/13 下午5:41
 */

@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Integer id;
    private String cron;
    private int shardingTotalCount;
    private String message;
}
