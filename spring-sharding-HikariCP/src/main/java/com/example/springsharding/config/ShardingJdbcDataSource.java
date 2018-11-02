package com.example.springsharding.config;

import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author xiaoma
 * @desc
 * @date 2018/10/7 下午12:14
 */
@Configuration
public class ShardingJdbcDataSource {

    @Bean
    public DataSource getShardingDataSource() throws SQLException, IOException {
        return MasterSlaveDataSourceFactory.createDataSource(ResourceUtils.getFile("classpath:sharding-jdbc.yml"));
    }

}


