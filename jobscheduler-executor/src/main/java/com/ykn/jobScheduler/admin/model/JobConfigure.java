package com.ykn.jobscheduler.admin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className JobConfigure
 * @date 2020/5/12 9:50
 **/
@Data
@TableName("job_configure")
@EqualsAndHashCode(callSuper = false)
public class JobConfigure implements Serializable {

    /**
     * 任务状态:禁用
     */
    public static final int STATUS_DISABLE = 0;

    /**
     * 任务状态:可用
     */
    public static final int STATUS_ENABLE = 1;


    public static final int LOG_OFF = 1;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("jobName")
    private String jobName;

    @TableField("httpUrl")
    private String httpUrl;

    @TableField("accessToken")
    private String accessToken;

    @TableField("groupId")
    private Long groupId;

    @TableField("closeBeginDate")
    private LocalDateTime closeBeginDate;

    @TableField("closeEndDate")
    private LocalDateTime closeEndDate;

    @TableField("creatAt")
    private LocalDateTime creatAt;

    @TableField("modifiedAt")
    private LocalDateTime modifiedAt;

    @TableField("state")
    private Integer state;

    @TableField("logOff")
    private Integer logOff;


    public JobConfigure() {
    }

    public JobConfigure(String jobName, String httpUrl, String accessToken, Long groupId, Integer state, Integer logOff) {
        this.jobName = jobName;
        this.httpUrl = httpUrl;
        this.creatAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
        this.groupId = groupId == null ? 0L : groupId;
        this.accessToken = accessToken == null ? "" : accessToken;
        this.state = state == null ? JobConfigure.STATUS_DISABLE : state;
        this.logOff = logOff == null ? JobConfigure.LOG_OFF : logOff;
    }
}
