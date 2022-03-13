package com.ykn.jobscheduler.executor.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ykn.jobscheduler.admin.model.JobConfigure;
import com.ykn.jobscheduler.admin.model.JobScheduleConfigure;
import com.ykn.jobscheduler.executor.api.JobService;
import com.ykn.jobscheduler.executor.impl.dao.JobConfigureDao;
import com.ykn.jobscheduler.executor.impl.dao.JobScheduleConfigureDao;
import com.ykn.jobscheduler.common.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className LocalJobServiceImpl
 * @date 2020/5/12 13:44
 **/
@Service
public class LocalJobServiceImpl implements JobService {

    @Resource
    private JobConfigureDao jobConfigureDao;

    @Resource
    private JobScheduleConfigureDao jobScheduleConfigureDao;

    @Override
    public List<JobInfo> listAllJob(String executorName) {
        return jobConfigureDao.listJob(executorName, JobConfigure.STATUS_ENABLE, JobScheduleConfigure.SCHEDULE_STATUS_NOT_STARTED);
    }

    @Override
    public ScheduleParam getScheduleParam(Long jobId) {
        return null;
    }

    @Override
    public HeartBeat heartBeat(HeartBeat beat) {
        return null;
    }

    @Override
    public void sendExecuteResult(JobResult result) {

    }

    @Override
    public void register(String executorName) {

    }

    @Override
    public void unregister(String executorName) {

    }

    @Override
    public void resetJobScheduleStatus(String executorName) {
        jobScheduleConfigureDao.updateState(executorName, JobScheduleConfigure.SCHEDULE_STATUS_NOT_STARTED);
    }

    @Override
    public JobInfo getJobById(Long id) {
        return jobConfigureDao.getJobInfoById(id);
    }

    @Override
    public boolean updateJobScheduleResult(Long id, LocalDateTime lastCompletionTime, Integer scheduleStatus, String failedMsg, Integer count) {
        LambdaUpdateWrapper<JobScheduleConfigure> update = new UpdateWrapper<JobScheduleConfigure>().lambda();
        if (lastCompletionTime != null) {
            update.set(JobScheduleConfigure::getLastCompletedTime, lastCompletionTime);
        }
        if (scheduleStatus != null) {
            update.set(JobScheduleConfigure::getState, scheduleStatus);
        }
        if (StringUtils.isNotBlank(failedMsg)) {
            update.set(JobScheduleConfigure::getRemark, failedMsg);
        }
        update.eq(JobScheduleConfigure::getJobId, id);
        return jobScheduleConfigureDao.update(null, update) == 1;
    }

    @Override
    public boolean updateJobStartingStatus(Long id, JobUpdateParam param) {
        LambdaUpdateWrapper<JobScheduleConfigure> update = new UpdateWrapper<JobScheduleConfigure>().lambda();

        if (param.getAfterScheduleStatus() != null) {
            update.set(JobScheduleConfigure::getState, param.getAfterScheduleStatus());
        }
        if (param.getLastScheduledTime() != null) {
            update.set(JobScheduleConfigure::getLastScheduleTime, param.getLastScheduledTime());
        }
        if (param.getNextExecutionTime() != null) {
            update.set(JobScheduleConfigure::getNextExecuteTime, param.getNextExecutionTime());
        }
        update.eq(JobScheduleConfigure::getJobId, id);
        update.eq(JobScheduleConfigure::getState, param.getCurrentStatusScheduleStatus());
        return jobScheduleConfigureDao.update(null, update) == 1;
    }
}
