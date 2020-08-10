package com.daop.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

/**
 * @BelongsProject: springboot_learn
 * @BelongsPackage: com.daop.scheduled.main
 * @Description: 定时任务线程池配置
 * @DATE: 2020-08-10
 * @AUTHOR: Administrator
 **/
@Configuration
public class ScheduleThreadPoolConfig {
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setSchedulerName("123456");
        // quartz参数
        Properties prop = new Properties();
        prop.put("org.quartz.scheduler.instanceName", "TestScheduler");
        prop.put("org.quartz.scheduler.instanceId", "Testt");

        factoryBean.setQuartzProperties(prop);

        factoryBean.setSchedulerName("TestScheduler");
        // 延时启动
        factoryBean.setStartupDelay(0);
        factoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        // 可选，QuartzScheduler
        factoryBean.setOverwriteExistingJobs(true);
        // 设置自动启动，默认为true
        factoryBean.setAutoStartup(true);

        return factoryBean;
    }
}
