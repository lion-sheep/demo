package com.demo.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoma
 * @desc
 * @date 2018/10/18 下午5:59
 */
@Configuration
public class CuratorFrameworkConfig {

    @Bean(name = "curatorFramework")
    public CuratorFramework curatorFramework() {
        String connectString = "localhost:2181";
        RetryPolicy retryPolicy = new RetryNTimes(10, 5000);
        CuratorFramework cf = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        cf.start();//建立连接
        return cf;
    }
}
