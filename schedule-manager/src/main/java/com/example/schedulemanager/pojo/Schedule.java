package com.example.schedulemanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/13 下午5:41
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Integer id;
    private String cron;
    private int shardingTotalCount;
    private String message;
}
