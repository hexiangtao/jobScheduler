package com.ykn.jobscheduler.executor.impl;

import com.ykn.jobscheduler.common.JobInfo;
import com.ykn.jobscheduler.executor.api.JobExecutor;
import com.ykn.jobscheduler.executor.api.JobScheduler;
import com.ykn.jobscheduler.executor.api.JobService;
import com.ykn.jobscheduler.executor.api.RunnableJob;
import com.ykn.jobscheduler.util.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className DefaultJobScheduler
 * @date 2020/5/12 10:09
 **/
public class DefaultJobScheduler implements JobScheduler {

    private static Logger log = LoggerFactory.getLogger(DefaultJobScheduler.class);

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private ThreadPoolTaskScheduler taskScheduler;
    private JobExecutor jobExecutor;

    private JobService jobService;
    private String executorName;


    public DefaultJobScheduler(JobService jobService) {
        this(jobService, "default");
    }

    public DefaultJobScheduler(JobService jobService, String executorName) {
        this(jobService, null, executorName);
    }

    public DefaultJobScheduler(JobService jobService, ThreadPoolTaskScheduler taskScheduler, String executorName) {
        this.jobService = jobService;
        this.executorName = executorName;
        if (taskScheduler == null) {
            this.taskScheduler = new ThreadPoolTaskScheduler();
            this.taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
            this.taskScheduler.setThreadNamePrefix(String.format("jobScheduler(%s)-", executorName));
        } else {
            this.taskScheduler = taskScheduler;
        }
        this.jobExecutor = new DefaultJobExecutor(jobService, this);
    }

    @PostConstruct
    public void init() {
        jobService.resetJobScheduleStatus(executorName);
        this.taskScheduler.initialize();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdownHook()));
        this.isRunning.compareAndSet(false, true);
    }

    @Scheduled(fixedRate = 1000L, initialDelay = 1000)
    public void start() {
        if (!isRunning.get()) {
            return;
        }
        List<JobInfo> jobConfigures = jobService.listAllJob(executorName);
        jobConfigures.stream().forEach(jobExecutor::execute);
    }


    @Override
    public void schedule(RunnableJob job, Date next) {
        taskScheduler.schedule(job, next);
    }

    private void shutdownHook() {
        isRunning.set(false);
        int awaitSeconds = 5;
        ScheduledThreadPoolExecutor executorService = this.taskScheduler.getScheduledThreadPoolExecutor();
        ThreadUtils.shutdown(executorService, awaitSeconds);
        Thread.currentThread().interrupt();
    }


    public boolean isRunning() {
        return isRunning.get();
    }

}
