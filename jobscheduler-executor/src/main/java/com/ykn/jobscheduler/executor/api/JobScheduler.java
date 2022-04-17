package com.ykn.jobscheduler.executor.api;

import java.util.Date;

/**
 * 执行器，负责调用job
 *
 * @author xiangtaohe
 * @version 1.0
 * @className JobScheduler
 * @date 2020/5/12 17:26
 **/
public interface JobScheduler {


    /**
     * 判断当前调度器是否在运行中
     *
     * @return
     */
    boolean isRunning();


    /**
     * 调度指定任务
     *
     * @param job  任务
     * @param next 日期
     */
    void schedule(RunnableJob job, Date next);
}
