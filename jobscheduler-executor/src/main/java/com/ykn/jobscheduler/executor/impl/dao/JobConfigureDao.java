package com.ykn.jobscheduler.executor.impl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ykn.jobscheduler.admin.model.JobConfigure;
import com.ykn.jobscheduler.common.JobInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className JobConfigureDao
 * @date 2020/5/12 13:51
 **/
@Mapper
public interface JobConfigureDao extends BaseMapper<JobConfigure> {

    String BASE_COLUMN = " t1.id,t1.jobName,t1.httpUrl,t1.state as jobStatus,t1.accessToken,t1.logOff,t2.cron,t2.state as scheduledStatus,t2.lastCompletedTime,t2.lastScheduleTime ";

    /**
     * 查询job列表
     * @Param executorId
     * @Param jobState
     * @Param scheduleState
     * @Return java.util.List<com.github.hxt.scheduler.common.JobInfo>
     **/
    @Select({"SELECT ", BASE_COLUMN, "FROM  job_configure  t1 INNER JOIN  job_schedule_configure  t2  ON  t1.id=t2.jobId", "WHERE   t2.executorId=#{executorId} AND t1.state=#{jobState} AND  t2.state=#{scheduleState}"
            , "AND (t1.closeBeginDate>NOW()  OR  t1.closeBeginDate<NOW()) "})
    List<JobInfo> listJob(@Param("executorId") String executorId, @Param("jobState") Integer jobState, @Param("scheduleState") Integer scheduleState);


    /**
     * 根据job id查询单项
     * @Param jobId
     * @Return com.github.hxt.scheduler.common.JobInfo
     **/
    @Select({"SELECT ", BASE_COLUMN, "FROM  job_configure  t1 INNER JOIN  job_schedule_configure  t2  ON  t1.id=t2.jobId", "WHERE   t1.id=#{jobId}"})
    JobInfo getJobInfoById(@Param("jobId") Long jobId);


    /**
     * 更新job状态
     * @Param id
     * @Param afterState
     * @Param state
     * @Return int
     **/
    @Update("UPDATE job_configure SET state=#{state} WHERE  id=#{} AND  state=#{beforeState}")
    int updateState(@Param("id") Long id, @Param("state") int afterState, @Param("beforeState") Integer state);
}
