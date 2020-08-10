package com.daop.quartz.quartz;

/**
 * @BelongsProject: springboot_learn
 * @BelongsPackage: com.daop.scheduled.config
 * @Description: 定时任务通用常量
 * @DATE: 2020-08-10
 * @AUTHOR: Administrator
 **/
public interface QuartzConstants {

    String TASK_CLASS_NAME = "TASK_CLASS_NAME";
    /**
     * 执行目标Key
     */
    String TASK_PROPERTIES = "TASK_PROPERTIES";
    /**
     * 默认
     */
    int MISFIRE_DEFAULT = 0;
    /**
     * 立即执行
     */
    int MISFIRE_IGNORE_MISFIRE = 1;
    /**
     * 执行一次
     */
    int MISFIRE_FIRE_AND_PROCEED = 2;
    /**
     * 放弃执行
     */
    int MISFIRE_DO_NOTHING = 3;

    /**
     * 定时任务状态枚举
     */
    enum JobStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);
        private Integer value;

        JobStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}