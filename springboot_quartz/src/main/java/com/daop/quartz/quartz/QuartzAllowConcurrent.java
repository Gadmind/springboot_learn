package com.daop.quartz.quartz;

import com.daop.quartz.pojo.JobDetail;
import com.daop.quartz.util.JobInvokeUtil;
import org.quartz.JobExecutionContext;

/**
 * @BelongsProject: xinyeiot
 * @BelongsPackage: com.xinye.iot.quartzJob
 * @Description: 支持并发的定时任务
 * @DATE: 2020-06-02
 * @AUTHOR: Administrator
 **/
public class QuartzAllowConcurrent extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, JobDetail jobDetail) throws Exception {
        JobInvokeUtil.invokeMethod(jobDetail);
    }
}
