package com.ykn.jobscheduler.executor.api;

import java.util.Date;

/**
 * TODO
 *
 * @author xiangtaohe
 * @version 1.0
 * @className JobScheduler
 * @date 2020/5/12 17:26
 **/
public interface JobScheduler {

    boolean isRunning();

    void schedule(RunnableJob job, Date next);
}
