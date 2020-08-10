package com.daop.quartz.pojo;

/**
 * @BelongsProject: springboot_learn
 * @BelongsPackage: com.daop.scheduled.pojo
 * @Description: 任务详情
 * @DATE: 2020-08-10
 * @AUTHOR: Administrator
 **/
public class JobDetail {
    private static final long serialVersionUID = 1L;
    /**
     * 定时任务ID
     */
    private Long jobId;

    /**
     * 定时任务表达式
     */
    private String jobCron;

    /**
     * 定时任务分组
     */
    private String jobGroup;

    /**
     * 定时任务名称
     */
    private String jobName;

    /**
     * 定时任务相关类名
     */
    private String jobClass;

    /**
     * 定时任务相关方法
     */
    private String jobMethod;

    /**
     * 定时任务定时任务状态（0正常 1停止）
     */
    private Integer jobStatus;

    /**
     * 定时任务是否并发执行（0并发 1非并发）
     */
    private Integer jobConcurrent;

    /**
     * 定时任务执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    private Integer jobMisfirePolicy;

    /**
     * 定时任务描述
     */
    private String jobDescription;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobMethod() {
        return jobMethod;
    }

    public void setJobMethod(String jobMethod) {
        this.jobMethod = jobMethod;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getJobConcurrent() {
        return jobConcurrent;
    }

    public void setJobConcurrent(Integer jobConcurrent) {
        this.jobConcurrent = jobConcurrent;
    }

    public Integer getJobMisfirePolicy() {
        return jobMisfirePolicy;
    }

    public void setJobMisfirePolicy(Integer jobMisfirePolicy) {
        this.jobMisfirePolicy = jobMisfirePolicy;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
}
