package com.ykn.jobscheduler.executor.api;

import com.ykn.jobscheduler.common.JobInfo;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className JobExecutor
 * @date 2020/5/12 10:06
 **/
public interface JobExecutor {


    /**
     * 执行任务
     **/
    void execute(JobInfo jobInfo);


    /**
     * 获取执行器当前状态
     *
     * @Return boolean
     **/
    boolean isRunning();
}
