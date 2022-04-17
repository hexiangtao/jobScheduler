package com.ykn.jobscheduler.executor.impl;

import com.ykn.jobscheduler.common.JobInfo;
import com.ykn.jobscheduler.executor.api.JobExecutor;
import com.ykn.jobscheduler.executor.api.JobService;
import com.ykn.jobscheduler.executor.api.RunnableJob;

import java.time.LocalDateTime;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className DefaultHttpJob
 * @date 2020/5/12 11:36
 **/
public class DefaultHttpJob extends RunnableJob {

    public DefaultHttpJob(JobInfo job, JobService jobService, JobExecutor jobScheduler) {
        super(job, jobService, jobScheduler);
    }

    @Override
    public int handle(JobInfo job) {
        try {
            System.out.println("now:"+LocalDateTime.now());
            Thread.sleep(1000*30L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
