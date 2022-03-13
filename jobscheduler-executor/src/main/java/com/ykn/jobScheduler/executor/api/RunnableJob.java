package com.ykn.jobscheduler.executor.api;

import com.ykn.jobscheduler.admin.model.JobConfigure;
import com.ykn.jobscheduler.admin.model.JobScheduleConfigure;
import com.ykn.jobscheduler.common.JobInfo;
import com.ykn.jobscheduler.common.JobUpdateParam;
import com.ykn.jobscheduler.util.FunctionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className RunnableJob
 * @date 2020/5/12 10:43
 **/
public abstract class RunnableJob implements Runnable {
    private Logger log = LoggerFactory.getLogger(RunnableJob.class);

    private final int MAX_RETRY_COUNT = 3;
    private final long RETRY_SLEEP_MILLS = 1000;

    private JobService jobService;
    private JobExecutor scheduler;
    private JobInfo jobInfo;

    public RunnableJob(JobInfo job, JobService jobService, JobExecutor jobScheduler) {
        this.jobService = jobService;
        this.scheduler = jobScheduler;
        this.jobInfo = job;
        init();
    }

    protected void init() {
    }

    @Override
    public void run() {
        try {
            if (!scheduler.isRunning() || !refreshJobStatus()) {
                return;
            }
            doJob();
        } catch (Throwable throwable) {
            log.error("execute jobInfo failed", throwable);
        }
    }

    private void doJob() throws Exception {
        logInfo("starting  jobInfo 【{}】", jobInfo.getJobName());
        boolean isSuccess = updateStatusAndExecutionTime(jobInfo.getId(), LocalDateTime.now());
        if (!isSuccess) {
            log.warn("execute jobInfo failed for jobInfo scheduled status invalid.");
            return;
        }
        int count = 0;
        int afterStatus = JobScheduleConfigure.SCHEDULE_STATUS_NOT_STARTED;
        String failedMsg = "";
        try {
            if (this.jobInfo == null || StringUtils.isBlank(this.jobInfo.getHttpUrl())) {
                log.error("jobInfo execution failed, httpUrl is not isBlank");
                afterStatus = JobScheduleConfigure.SCHEDULE_STATUS_NOT_STARTED;
            } else {
                count = handle(jobInfo);
            }
        } catch (Throwable throwable) {
            log.error("jobInfo execution failed", throwable);
            afterStatus = JobScheduleConfigure.SCHEDULE_STATUS_NOT_STARTED;
            failedMsg = throwable.getMessage();
        }
        logInfo("finished  jobInfo:【{}】 ", jobInfo.getJobName());
        updateScheduleResult(afterStatus, failedMsg, count);
    }

    private boolean refreshJobStatus() {
        this.jobInfo = jobService.getJobById(jobInfo.getId());
        if (jobService == null) {
            log.warn(" jobInfo interrupted. the jobInfo maybe deleted. id:{}", jobInfo.getId());
            return false;
        }
        if (jobInfo.getJobStatus() != null && jobInfo.getJobStatus() == JobConfigure.STATUS_DISABLE) {
            JobUpdateParam param = new JobUpdateParam(JobScheduleConfigure.SCHEDULE_STATUS_NOT_STARTED, JobScheduleConfigure.SCHEDULE_STATUS_PREPARED);
            jobService.updateJobStartingStatus(jobInfo.getId(), param);
            log.warn(" jobInfo interrupted. the status was changed. id:{}", jobInfo.getId());
            return false;
        }
        return true;
    }

    /**
     * 更新任务执行前状态
     *
     * @param id
     * @param executionTimes
     */
    private boolean updateStatusAndExecutionTime(Long id, LocalDateTime executionTimes) throws Exception {
        final int afterStatus = JobScheduleConfigure.SCHEDULE_STATUS_PROCESSING;
        final int beforeStatus = JobScheduleConfigure.SCHEDULE_STATUS_PREPARED;
        JobUpdateParam jobUpdateParam = new JobUpdateParam(afterStatus, beforeStatus);
        jobUpdateParam.setLastScheduledTime(executionTimes);
        Callable<Boolean> call = () -> jobService.updateJobStartingStatus(id, jobUpdateParam);
        return FunctionUtils.retryIfException(RETRY_SLEEP_MILLS, MAX_RETRY_COUNT, call);
    }

    /**
     * 更新任务执行后状态
     *
     * @param afterStatus
     * @param failedMsg
     */
    private boolean updateScheduleResult(Integer afterStatus, String failedMsg, int count) throws Exception {
        Callable<Boolean> call = () -> jobService.updateJobScheduleResult(jobInfo.getId(), LocalDateTime.now(), afterStatus, failedMsg, count);
        return FunctionUtils.retryIfException(RETRY_SLEEP_MILLS, MAX_RETRY_COUNT, call);
    }


    /**
     * 打印info级日志，可在配置参数里关闭
     *
     * @param format
     * @param arguments
     */
    protected void logInfo(String format, Object... arguments) {
        if (jobInfo.getLogOff() == null || jobInfo.getLogOff() != JobConfigure.LOG_OFF) {
            log.info(format, arguments);
        }
    }


    public abstract int handle(JobInfo job);
}
