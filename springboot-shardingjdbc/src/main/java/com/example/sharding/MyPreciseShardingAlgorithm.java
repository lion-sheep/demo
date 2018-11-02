package com.example.sharding;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;
import java.util.Objects;

/**
 * 自定义分片算法
 * 
 * @author yinjihuan
 *
 */
public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        for (String tableName : availableTargetNames) {
            if (tableName.endsWith(Math.abs(Objects.hash(shardingValue.getValue())) % 4 + "")) {
                return tableName;
            }
        }
        throw new IllegalArgumentException();
    }

}

