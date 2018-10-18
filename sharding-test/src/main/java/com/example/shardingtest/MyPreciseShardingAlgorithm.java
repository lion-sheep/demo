package com.example.shardingtest;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;
import java.util.Objects;

/**
 * @author xiaoma
 * @desc
 * @date 2018/10/6 下午10:16
 */
public class StringHash implements PreciseShardingAlgorithm<String> {
    @Override
    public String doSharding(
            Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        String tableSharding = Math.abs(Objects.hash(shardingValue.getValue()) % 4) + "";
        for(String tableName : availableTargetNames){
            if(tableName.endsWith(tableSharding)){
                return tableName;
            }
        }
        throw new IllegalArgumentException();
    }
}
