package com.ykn.jobscheduler.executor.impl;

import com.ykn.jobscheduler.admin.model.JobScheduleConfigure;
import com.ykn.jobscheduler.common.JobInfo;
import com.ykn.jobscheduler.common.JobUpdateParam;
import com.ykn.jobscheduler.executor.api.JobExecutor;
import com.ykn.jobscheduler.executor.api.JobScheduler;
import com.ykn.jobscheduler.executor.api.JobService;
import com.ykn.jobscheduler.executor.api.RunnableJob;
import com.ykn.jobscheduler.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className DefaultJobExecutor
 * @date 2020/5/12 17:21
 **/
public class DefaultJobExecutor implements JobExecutor {
    private static Logger log = LoggerFactory.getLogger(DefaultJobExecutor.class);


    private JobService jobService;
    private JobScheduler jobScheduler;


    public DefaultJobExecutor(JobService jobService, JobScheduler jobScheduler) {
        this.jobService = jobService;
        this.jobScheduler = jobScheduler;
    }

    @Override
    public void execute(JobInfo jobInfo) {
        RunnableJob job = createJob(jobInfo);
        if (job == null) {
            return;
        }

        Date next = nextExecutionTime(jobInfo);
        JobUpdateParam param = new JobUpdateParam(JobScheduleConfigure.SCHEDULE_STATUS_PREPARED, JobScheduleConfigure.SCHEDULE_STATUS_NOT_STARTED);
        param.setNextExecutionTime(DateUtil.toLocalDateTime(next));
        boolean success = jobService.updateJobStartingStatus(jobInfo.getId(), param);
        if (!success) {
            return;
        }
        jobScheduler.schedule(job, next);
    }


    private RunnableJob createJob(JobInfo jobConfigure) {
        try {
            return new DefaultHttpJob(jobConfigure, jobService, this);
        } catch (Throwable ex) {
            log.error("createJob failed", ex);
            return null;
        }
    }

    private Date nextExecutionTime(JobInfo jobInfo) {
        CronSequenceGenerator sequenceGenerator = new CronSequenceGenerator(jobInfo.getCron());
        LocalDateTime date = jobInfo.getLastCompletedTime();
        if (date == null) {
            return sequenceGenerator.next(Date.from(Instant.ofEpochMilli(System.currentTimeMillis())));
        }
        LocalDateTime lastScheduleTime = jobInfo.getLastScheduleTime();
        if (lastScheduleTime != null && date.isBefore(lastScheduleTime)) {
            date = lastScheduleTime;
        }
        return sequenceGenerator.next(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
    }

    @Override
    public boolean isRunning() {
        return jobScheduler.isRunning();
    }
}
