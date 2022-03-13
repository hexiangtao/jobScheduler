package com.ykn.jobscheduler.executor.api;

import com.ykn.jobscheduler.common.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className JobService
 * @date 2020/5/12 9:26
 **/
public interface JobService {


    /**
     * 查询执行器所有job
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 9:27
     * @Version 1.0
     * @Param executorName
     * @Return java.util.List<com.github.hxt.scheduler.common.JobConfigure>
     **/
    List<JobInfo> listAllJob(String executorName);


    /**
     * 查询具体job的调度参数
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 9:29
     * @Version 1.0
     * @Param jobId
     * @Return com.github.hxt.scheduler.common.ScheduleParam
     **/
    ScheduleParam getScheduleParam(Long jobId);


    /**
     * 执行器向中心服务器发送心跳
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 9:30
     * @Version 1.0
     * @Param beat
     * @Return com.github.hxt.scheduler.common.HeartBeat
     **/
    HeartBeat heartBeat(HeartBeat beat);


    /**
     * 发送执行结果
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 9:32
     * @Version 1.0
     * @Param result
     * @Return void
     **/
    void sendExecuteResult(JobResult result);


    /**
     * 上线，注册到调度中心
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 9:34
     * @Version 1.0
     * @Param executorName
     * @Return void
     **/
    void register(String executorName);


    /**
     * 注销(下线)
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 9:35
     * @Version 1.0
     * @Param ip
     * @Return void
     **/
    void unregister(String executorName);


    /**
     * 重置job调度状态
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 10:11
     * @Version 1.0
     * @Param executorName
     * @Return void
     **/
    void resetJobScheduleStatus(String executorName);


    /**
     * 根据ID查询job信息
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 11:12
     * @Version 1.0
     * @Param id
     * @Return com.github.hxt.scheduler.common.JobConfigure
     **/
    JobInfo getJobById(Long id);


    /**
     * 更新任何执行结果
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 11:17
     * @Version 1.0
     * @Param id
     * @Param lastCompletionTime
     * @Param scheduleStatus
     * @Param failedMsg
     * @Param count
     * @Return boolean
     **/
    boolean updateJobScheduleResult(Long id, LocalDateTime lastCompletionTime, Integer scheduleStatus, String failedMsg, Integer count);


    /**
     * 更新任务启动时状态
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 11:19
     * @Version 1.0
     * @Param id
     * @Param param
     * @Return boolean
     **/
    boolean updateJobStartingStatus(Long id, JobUpdateParam param);

}
