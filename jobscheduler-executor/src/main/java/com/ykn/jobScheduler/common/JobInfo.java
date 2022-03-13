package com.ykn.jobscheduler.common;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className JobConfigure
 * @date 2020/5/12 9:27
 **/
@Data
public class JobInfo {


    private Long id;

    private String jobName;

    private String httpUrl;

    private Integer jobStatus;

    private  String accessToken;

    private Integer logOff;

    private String cron;

    private Integer scheduledStatus;

    private LocalDateTime lastCompletedTime;

    private LocalDateTime lastScheduleTime;


}
