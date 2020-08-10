package com.daop.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

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
    public ScheduledExecutorFactoryBean scheduledExecutorFactoryBean(){
        ScheduledExecutorFactoryBean factoryBean = new ScheduledExecutorFactoryBean();
        factoryBean.setPoolSize(20);
        factoryBean.setThreadNamePrefix("task thread - ");
        return factoryBean;
    }
}
