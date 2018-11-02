package com.example.schedulemanager.job;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/13 下午5:04
 */
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperRegistryCenterConfig {
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

    @Bean(initMethod = "init", name = "zookeeperRegistryCenter")
    public ZookeeperRegistryCenter zookeeperRegistryCenter() {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverLists, namespace));
    }
}
