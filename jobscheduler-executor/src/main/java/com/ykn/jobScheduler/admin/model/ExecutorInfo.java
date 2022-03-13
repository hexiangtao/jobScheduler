package com.ykn.jobscheduler.admin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className ExecutorInfo
 * @date 2020/5/13 11:00
 **/

@Data
@TableName("executor")
@EqualsAndHashCode
public class ExecutorInfo {


    /**
     * 执行器标识
     **/
    private String id;

    /**
     * 活动线程数
     **/
    private int activeCount;

    /**
     * 线程池数
     **/
    private int poolSize;

    /**
     * core线程数
     **/
    private int corePoolSize;

    /**
     * 任务数
     **/
    private long taskCount;

    /**
     * 已完成任务数
     **/
    private long completedTaskCount;


    private int largestPoolSize;

    private int maximumPoolSize;


    /**
     * 上一次上线时间
     **/
    private LocalDateTime lastUpDate;


    /**
     * 上线次发送心跳时间
     **/
    private LocalDateTime lastBeatDate;


}
