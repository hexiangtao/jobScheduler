package com.ykn.jobscheduler.admin.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className JobQueryRequest
 * @date 2020/5/13 10:40
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JobQry implements Serializable {

    private Long id;

    private String jobName;

    private String accessToken;

    private Long groupId;

    private Integer state;

    private Integer logOff;
}
