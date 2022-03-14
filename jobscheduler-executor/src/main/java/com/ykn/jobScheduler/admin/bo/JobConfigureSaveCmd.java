package com.ykn.jobscheduler.admin.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className JobConfigureSaveRequest
 * @date 2020/5/12 18:13
 **/
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class JobConfigureSaveCmd implements Serializable {


    private Long id;

    private String jobName;

    private String httpUrl;

    private String accessToken;

    private Long groupId;

    private String closeBeginDate;

    private String closeEndDate;

    private Integer state;

    private Integer logOff;



}
