package com.daop.quartz.quartz;

import com.daop.quartz.pojo.JobDetail;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @BelongsProject: xinyeiot
 * @BelongsPackage: com.xinye.iot.quartzJob
 * @Description: 抽象quartz类调用
 * @DATE: 2020-06-02
 * @AUTHOR: Administrator
 **/
public abstract class AbstractQuartzJob implements Job {
    Logger log= LoggerFactory.getLogger(AbstractQuartzJob.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = new JobDetail();
        BeanUtils.copyProperties( context.getMergedJobDataMap().get(QuartzConstants.TASK_PROPERTIES),jobDetail);
        try {
            //TODO 执行前的操作
            if (jobDetail != null) {
                doExecute(context, jobDetail);
            }
            //TODO 执行后的操作
        } catch (Exception e) {
            log.error("任务执行异常 ::", e);
            //TODO 异常之后的执行操作
        }
    }

    /**
     * 执行前
     *
     * @param context   工作执行上下文
     * @param jobDetail 定时任务信息类
     */
    protected void before(JobExecutionContext context, JobDetail jobDetail) {
    }

    /**
     * 执行后
     *
     * @param context   工作执行上下文
     * @param jobDetail 定时任务信息类
     * @param e         异常
     */
    protected void after(JobExecutionContext context, JobDetail jobDetail, Exception e) {
        if (e != null) {

        } else {

        }
    }

    /**
     * 执行方法 子类重载
     *
     * @param context   上下文对象
     * @param jobDetail 定时任务信息类
     * @throws Exception 执行过程中异常
     */
    protected abstract void doExecute(JobExecutionContext context, JobDetail jobDetail) throws Exception;
}
