package com.ykn.jobscheduler.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ykn.jobscheduler.admin.bo.JobConfigureSaveRequest;
import com.ykn.jobscheduler.admin.bo.JobQueryRequest;
import com.ykn.jobscheduler.admin.bo.ScheduleSaveRequest;
import com.ykn.jobscheduler.admin.model.JobConfigure;
import com.ykn.jobscheduler.admin.model.JobScheduleConfigure;
import com.ykn.jobscheduler.common.ThreadPoolInfo;
import com.ykn.jobscheduler.executor.impl.dao.JobConfigureDao;
import com.ykn.jobscheduler.executor.impl.dao.JobScheduleConfigureDao;
import com.ykn.jobscheduler.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className JobConfigService
 * @date 2020/5/12 18:14
 **/
@Service
public class JobConfigService {


    @Resource
    private JobConfigureDao jobConfigureDao;

    @Resource
    private JobScheduleConfigureDao jobScheduleConfigureDao;

    public static ThreadPoolInfo getThreadPoolInfo() {
        return null;
    }

    @Transactional
    public Long saveJobConfigure(JobConfigureSaveRequest req) {
        if (req.getId() != null) {
            updateJobConfigure(req);
            return req.getId();
        }

        if (StringUtils.isBlank(req.getJobName()) || StringUtils.isBlank(req.getHttpUrl())) {
            throw new IllegalArgumentException("jobName,httpUrl must be present");
        }

        if (StringUtils.isBlank(req.getCloseEndDate()) || StringUtils.isBlank(req.getCloseBeginDate())) {
            throw new IllegalArgumentException("closeBeginDate,closeEndDate must be present");
        }

        String jobName = req.getJobName(), httpUrl = req.getHttpUrl(), accessToken = req.getAccessToken();
        Integer state = req.getState(), logOff = req.getLogOff();
        Long groupId = req.getGroupId();
        JobConfigure jobConfigure = new JobConfigure(jobName, httpUrl, accessToken, groupId, state, logOff);

        LocalDateTime closeBeginDate = DateUtil.toLocalDateTime(req.getCloseBeginDate());
        LocalDateTime closeEndDate = DateUtil.toLocalDateTime(req.getCloseEndDate());

        if (closeBeginDate.isAfter(closeEndDate) || closeBeginDate.isEqual(closeEndDate)) {
            throw new IllegalArgumentException("closeBeginDate must be before closeEndDate");
        }

        jobConfigure.setCloseBeginDate(closeBeginDate);
        jobConfigure.setCloseEndDate(closeEndDate);
        jobConfigureDao.insert(jobConfigure);
        return jobConfigure.getId();
    }


    public void saveScheduleConfigure(ScheduleSaveRequest request) {

        JobConfigure jobConfigure = jobConfigureDao.selectById(request.getJobId());
        if (jobConfigure == null) {
            throw new IllegalArgumentException("job not exists");
        }

        JobScheduleConfigure configure = jobScheduleConfigureDao.selectById(request.getJobId());
        if (configure != null) {

            if (StringUtils.isNotBlank(request.getCron())) {
                configure.setCron(request.getCron());
            }
            if (request.getRemark() != null) {
                configure.setRemark(request.getRemark());
            }
            if (request.getState() != null) {
                configure.setState(request.getState());
            }
            if (request.getExecutorId() != null) {
                configure.setExecutorId(request.getExecutorId());
            }
            jobScheduleConfigureDao.updateById(configure);
            return;
        }

        if (StringUtils.isBlank(request.getCron()) || request.getState() == null) {
            throw new IllegalArgumentException("parameter invalid");
        }
        JobScheduleConfigure newItem = new JobScheduleConfigure(request.getJobId(), request.getCron(), request.getState());

        if (StringUtils.isNotBlank(request.getExecutorId())) {
            newItem.setExecutorId(request.getExecutorId());
        }
        if (request.getRemark() != null) {
            newItem.setRemark(request.getRemark());
        }
        jobScheduleConfigureDao.insert(newItem);

    }

    public List<JobConfigure> listAllJob(JobQueryRequest request) {
        LambdaQueryWrapper<JobConfigure> query = Wrappers.lambdaQuery();
        if (request.getId() != null) {
            query.eq(JobConfigure::getId, request.getId());
        }

        if (request.getAccessToken() != null) {
            query.eq(JobConfigure::getAccessToken, request.getAccessToken());
        }
        if (request.getGroupId() != null) {
            query.eq(JobConfigure::getGroupId, request.getGroupId());
        }
        if (StringUtils.isNotBlank(request.getJobName())) {
            query.eq(JobConfigure::getJobName, request.getJobName());
        }
        if (request.getLogOff() != null) {
            query.eq(JobConfigure::getLogOff, request.getLogOff());
        }
        query.orderByDesc(JobConfigure::getId);
        return jobConfigureDao.selectList(query);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateJobStatus(Long id, int status) {
        JobConfigure jobConfigure = jobConfigureDao.selectById(id);
        if (jobConfigure == null) {
            throw new IllegalArgumentException("record not found,id=" + id);
        }
        boolean affected = jobConfigureDao.updateState(id, status, jobConfigure.getState()) == 1;
        if (!affected) {
            throw new IllegalArgumentException("affected 0 rows");
        }
    }


    private void updateJobConfigure(JobConfigureSaveRequest configure) {
        JobConfigure jobConfigure = jobConfigureDao.selectById(configure.getId());
        if (jobConfigure == null) {
            throw new IllegalArgumentException("could not  found jobConfigure,that id is " + configure.getId());
        }
        LambdaUpdateWrapper<JobConfigure> update = prepareJobUpdateWrapper(jobConfigure, configure);
        jobConfigureDao.update(null, update);
    }

    private LambdaUpdateWrapper<JobConfigure> prepareJobUpdateWrapper(JobConfigure jobConfigure, JobConfigureSaveRequest request) {

        if (StringUtils.isNotBlank(request.getCloseBeginDate()) && StringUtils.isNotBlank(request.getCloseEndDate())) {
            if (request.getCloseBeginDate().compareTo(request.getCloseEndDate()) >= 0) {
                throw new IllegalArgumentException("closeBeginDate must before closeEndDate");
            }
        }
        LambdaUpdateWrapper<JobConfigure> update = new UpdateWrapper<JobConfigure>().lambda();
        if (request.getAccessToken() != null) {
            update.set(JobConfigure::getAccessToken, request.getAccessToken());
        }

        if (StringUtils.isNotBlank(request.getCloseBeginDate())) {
            LocalDateTime closeBeginDate = DateUtil.toLocalDateTime(request.getCloseBeginDate());
            if (closeBeginDate.isAfter(jobConfigure.getCloseEndDate())) {
                throw new IllegalArgumentException("closeBeginDate is after closeEndDate");
            }
            update.set(JobConfigure::getCloseBeginDate, closeBeginDate);
        }

        if (StringUtils.isNotBlank(request.getCloseEndDate())) {
            LocalDateTime closeEndDate = DateUtil.toLocalDateTime(request.getCloseEndDate());
            if (closeEndDate.isBefore(jobConfigure.getCloseBeginDate())) {
                throw new IllegalArgumentException("closeEndDate is before closeBeginDate");
            }
            update.set(JobConfigure::getCloseBeginDate, closeEndDate);
        }

        if (request.getGroupId() != null) {
            update.set(JobConfigure::getGroupId, request.getGroupId());
        }

        if (StringUtils.isBlank(request.getHttpUrl())) {
            update.set(JobConfigure::getHttpUrl, request.getHttpUrl());
        }

        if (StringUtils.isNotBlank(request.getJobName())) {
            update.set(JobConfigure::getJobName, request.getJobName());
        }
        if (request.getLogOff() != null) {
            update.set(JobConfigure::getLogOff, request.getLogOff());
        }

        if (request.getState() != null) {
            update.set(JobConfigure::getState, request.getState());
        }
        update.eq(JobConfigure::getId, jobConfigure.getId());
        return update;
    }

}
