package com.ykn.jobscheduler.config;

import com.ykn.jobscheduler.executor.api.JobService;
import com.ykn.jobscheduler.executor.impl.DefaultJobScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className JobSchedulerConfigure
 * @date 2020/5/12 14:18
 **/
@Configuration
public class JobSchedulerConfigure {


    @Bean
    public DefaultJobScheduler jobScheduler(JobService jobService) {
        return new DefaultJobScheduler(jobService);
    }
}
