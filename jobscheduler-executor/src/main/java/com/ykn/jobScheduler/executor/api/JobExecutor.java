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
     * 调用任务
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 17:18
     * @Version 1.0
     * @Param jobInfo
     * @Return void
     **/
    void execute(JobInfo jobInfo);


    /**
     * 获取执行器当前状态
     *
     * @Author xiangtaohe
     * @Date 2020/5/12 17:18
     * @Version 1.0
     * @Param
     * @Return boolean
     **/
    boolean isRunning();
}
