package com.daop.quartz.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: springboot_learn
 * @BelongsPackage: com.daop.quartz.main
 * @Description:
 * @DATE: 2020-08-10
 * @AUTHOR: Administrator
 **/
@Component("taskTest")
public class TaskTest {
    Logger log = LoggerFactory.getLogger(TaskTest.class);

    public void testMethod() {
        log.info("每隔5秒执行一次, 当前线程名称{}", Thread.currentThread().getName());
    }
}
