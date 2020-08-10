package com.daop.quartz.util;

import com.daop.quartz.quartz.QuartzConstants;
import com.daop.quartz.pojo.JobDetail;
import com.daop.quartz.quartz.QuartzAllowConcurrent;
import com.daop.quartz.quartz.QuartzDisallowConcurrent;
import org.quartz.*;

/**
 * @BelongsProject: springboot_learn
 * @BelongsPackage: com.daop.scheduled.util
 * @Description: 定时任务工具类
 * @DATE: 2020-08-10
 * @AUTHOR: Administrator
 **/
public class QuartzUtil {
    /**
     * 判断该定时任务是否支持并发
     *
     * @param jobDetail 定时任务信息类
     * @return QuartzAllowConcurrent 支持并发
     * QuartzDisallowConcurrent 不支持并发
     */
    private static Class<? extends Job> getQuartzJobClass(JobDetail jobDetail) {
        boolean concurrent = "0".equals(jobDetail.getJobConcurrent());
        return concurrent ? QuartzAllowConcurrent.class : QuartzDisallowConcurrent.class;
    }

    /**
     * 创建定时任务，定时任务创建
     *
     * @param scheduler       调度器
     * @param quartzJobDetail 定时任务信息类
     */
    public static void createScheduleJob(Scheduler scheduler, JobDetail quartzJobDetail) {
        try {
            //获取定时任务的执行类
            Class<? extends Job> jobClass = getQuartzJobClass(quartzJobDetail);

            Long jobId = Long.valueOf(quartzJobDetail.getJobId());
            String jobGroup = quartzJobDetail.getJobGroup();

            //构建定时任务信息
            org.quartz.JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(getJobKey(jobId, jobGroup))
                    .withDescription(quartzJobDetail.getJobDescription())
                    .build();

            //设置定时任务指定方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJobDetail.getJobCron());
            //设置定时任务错误执行策略
            scheduleBuilder = handleCronScheduleMisfirePolicy(quartzJobDetail, scheduleBuilder);
            //创建定时任务触发器
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(jobId, jobGroup))
                    .withSchedule(scheduleBuilder)
                    .build();
            //放入参数，运行时的方法可获取
            jobDetail.getJobDataMap().put(QuartzConstants.TASK_PROPERTIES, quartzJobDetail);
            //判断任务是否存在
            if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
                //防止创建时存在数据问题 先移除，然后在执行创建操作
                scheduler.deleteJob(getJobKey(jobId, jobGroup));
            }

            //创建定时任务
            scheduler.scheduleJob(jobDetail, trigger);

            //判断任务状态
            if (quartzJobDetail.getJobStatus().equals(QuartzConstants.JobStatus.PAUSE.getValue())) {
                scheduler.pauseJob(getJobKey(jobId, jobGroup));
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建任务触发对象
     *
     * @param jobId    任务ID
     * @param jobGroup 任务分组
     * @return 触发对象键
     */
    public static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
        return TriggerKey.triggerKey(QuartzConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 构建任务键对象
     *
     * @param jobId    任务ID
     * @param jobGroup 任务分组
     * @return 任务键对象
     */
    public static JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey(QuartzConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 定时任务策略
     *
     * @param jobDetail           定时任务信息类
     * @param cronScheduleBuilder 调度器
     * @return 调度器
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(JobDetail jobDetail, CronScheduleBuilder cronScheduleBuilder) {
        switch (jobDetail.getJobMisfirePolicy()) {
            case QuartzConstants.MISFIRE_DEFAULT:
                return cronScheduleBuilder;
            //忽略错误立即执行
            case QuartzConstants.MISFIRE_IGNORE_MISFIRE:
                return cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
            //任务执行一次
            case QuartzConstants.MISFIRE_FIRE_AND_PROCEED:
                return cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
            //放弃执行
            case QuartzConstants.MISFIRE_DO_NOTHING:
                return cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
            default:
                throw new RuntimeException("定时任务执行错误");
        }
    }
}
