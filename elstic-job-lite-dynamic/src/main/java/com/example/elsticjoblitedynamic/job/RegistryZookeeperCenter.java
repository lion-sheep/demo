package com.example.springbootelasticjob.job;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册中心配置
 * 用于注册和协调作业分布式行为的组件，目前仅支持Zookeeper。
 */
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class RegistryZookeeperCenter {

    private String serverLists;

    private String namespace;

    public String getServerLists() {
        return serverLists;
    }

    public void setServerLists(String serverLists) {
        this.serverLists = serverLists;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Bean(name = "zookeeperRegistryCenter", initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter() {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverLists, namespace));
    }
}
