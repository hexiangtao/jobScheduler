package com.ykn.jobscheduler.admin.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className ScheduleSaveRequest
 * @date 2020/5/15 18:22
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class ScheduleSaveRequest implements Serializable {

    private Long jobId;

    private String cron;

    private Integer state;

    private String executorId;

    private String remark;

}
