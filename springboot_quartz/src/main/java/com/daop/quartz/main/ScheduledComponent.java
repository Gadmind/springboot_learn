package com.daop.quartz.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongsProject: springboot_learn
 * @BelongsPackage: com.daop.scheduled.main
 * @Description: 通过spring_boot自带的Scheduled定时任务
 * @DATE: 2020-08-10
 * @AUTHOR: Administrator
 **/
@Component
public class ScheduledComponent {
    private Logger log = LoggerFactory.getLogger(ScheduledComponent.class);
    private AtomicInteger taskNumber = new AtomicInteger();

    @Scheduled(cron = "*/5 * * * * ?")
    public void task1() {
        log.info("每隔5秒执行一次, 当前线程名称{} 当前执行次数{}", Thread.currentThread().getName(), taskNumber.incrementAndGet());
    }
}
