package com.ykn.jobscheduler.common;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: hexiangtao
 * @date: 2020/3/26 21:09
 */
@Data
public class JobUpdateParam {


    private LocalDateTime lastScheduledTime;

    private Integer afterScheduleStatus;

    private int currentStatusScheduleStatus;

    private LocalDateTime nextExecutionTime;

    public JobUpdateParam(Integer afterScheduleStatus, int currentStatusScheduleStatus) {
        this.afterScheduleStatus = afterScheduleStatus;
        this.currentStatusScheduleStatus = currentStatusScheduleStatus;
    }
}
