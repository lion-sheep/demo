package com.example.schedulemanager.job;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.example.schedulemanager.pojo.Schedule;
import com.example.schedulemanager.utils.ApplicationContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/13 下午5:31
 */

@Component
public class JobSchedulerConfig {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;
    @Autowired
    private JobEventConfiguration jobEventConfiguration;
    @Autowired
    private LiteJobConfig liteJobConfig;
    @Autowired
    private ApplicationContextUtil applicationContextUtil;

    public void addJobSchedulerBeanInApplicationContext(Schedule schedule) {
        SimpleJob simpleJob = new TaskSimpleJob(schedule.getMessage());
        LiteJobConfiguration liteJobConfiguration =
                liteJobConfig.liteJobConfiguration(simpleJob.getClass(), schedule.getCron(), schedule.getShardingTotalCount());
        JobScheduler jobScheduler = new SpringJobScheduler(simpleJob, zookeeperRegistryCenter, liteJobConfiguration, jobEventConfiguration);
        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(JobScheduler.class, () -> jobScheduler);
        beanDefinitionBuilder.setInitMethodName("init");
        ConfigurableApplicationContext configurableApplicationContext =
                (ConfigurableApplicationContext) applicationContextUtil.getApplicationContext();
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        // 注册 bean
        defaultListableBeanFactory.registerBeanDefinition(schedule.getId().toString(), beanDefinitionBuilder.getRawBeanDefinition());
    }

    public void destroyJobSchedulerBeanInApplicationContext(Schedule schedule) {
        ConfigurableApplicationContext configurableApplicationContext =
                (ConfigurableApplicationContext) applicationContextUtil.getApplicationContext();
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        defaultListableBeanFactory.destroySingleton(schedule.getId().toString());
        addJobSchedulerBeanInApplicationContext(schedule);
    }
}
