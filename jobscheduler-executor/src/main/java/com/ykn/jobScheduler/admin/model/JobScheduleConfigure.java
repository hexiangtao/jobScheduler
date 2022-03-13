package com.ykn.jobscheduler.admin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className JobScheduleConfigure
 * @date 2020/5/12 9:51
 **/
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@TableName("job_schedule_configure")
public class JobScheduleConfigure implements Serializable {


    /**
     * 调度状态:未开始
     */
    public static final int SCHEDULE_STATUS_NOT_STARTED = 0;

    /**
     * 调度状态:准备中
     */
    public static final int SCHEDULE_STATUS_PREPARED = 1;
    /**
     * 调度状态:处理中
     */
    public static final int SCHEDULE_STATUS_PROCESSING = 2;


    @TableId("jobId")
    private Long jobId;

    @TableField("cron")
    private String cron;

    @TableField("state")
    private Integer state;

    @TableField("executorId")
    private String executorId;

    @TableField("lastCompletedTime")
    private LocalDateTime lastCompletedTime;

    @TableField("lastScheduleTime")
    private LocalDateTime lastScheduleTime;

    @TableField("nextExecuteTime")
    private LocalDateTime nextExecuteTime;

    @TableField("remark")
    private String remark;

    @TableField("createAt")
    private LocalDateTime createAt;

    public JobScheduleConfigure() {
    }

    public JobScheduleConfigure(Long jobId, String cron, Integer state) {
        this.jobId = jobId;
        this.cron = cron;
        this.state = state;
        this.createAt = LocalDateTime.now();
        this.lastCompletedTime = null;
        this.lastScheduleTime = null;
        this.nextExecuteTime = null;
        this.remark = "";
        this.executorId = "";

    }
}
