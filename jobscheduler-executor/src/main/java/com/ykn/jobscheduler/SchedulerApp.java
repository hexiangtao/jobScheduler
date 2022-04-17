package com.ykn.jobscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className SchedulerApp
 * @date 2020/5/12 13:45
 **/

@EnableTransactionManagement
@SpringBootApplication
@EnableScheduling
public class SchedulerApp {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerApp.class);
    }


}
