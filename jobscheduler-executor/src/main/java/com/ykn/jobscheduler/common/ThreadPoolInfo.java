package com.ykn.jobscheduler.common;

import lombok.Data;

/**
 * 线程池信息
 *
 * @author: hexiangtao
 * @date: 2020/3/26 20:09
 */
@Data
public class ThreadPoolInfo {
    private int activeCount;

    private int poolSize;

    private int corePoolSize;

    private long taskCount;

    private long completedTaskCount;

    private int largestPoolSize;

    private int maximumPoolSize;


}
