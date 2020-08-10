package com.daop.quartz.main;

import com.daop.quartz.pojo.JobDetail;
import com.daop.quartz.util.QuartzUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
    @Resource
    private Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException {

        log.info("=======初始化定时任务=======");
        scheduler.clear();
        JobDetail quartzJob = new JobDetail();
        quartzJob.setJobId(1L);
        quartzJob.setJobCron("*/5 * * * * ?");
        quartzJob.setJobGroup("INSTRUMENT");
        quartzJob.setJobName("测试定时任务");
        quartzJob.setJobClass("taskTest");
        quartzJob.setJobMethod("testMethod");
        quartzJob.setJobStatus(0);
        quartzJob.setJobConcurrent(1);
        quartzJob.setJobMisfirePolicy(3);
        quartzJob.setJobDescription("sendTo0301");
        QuartzUtil.createScheduleJob(scheduler, quartzJob);
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void task1() {
        log.info("每隔5秒执行一次, 当前线程名称{} 当前执行次数{}", Thread.currentThread().getName(), taskNumber.incrementAndGet());
    }
}
