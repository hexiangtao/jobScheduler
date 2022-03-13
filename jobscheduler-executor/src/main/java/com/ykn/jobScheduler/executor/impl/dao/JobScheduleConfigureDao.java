package com.ykn.jobscheduler.executor.impl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ykn.jobscheduler.admin.model.JobScheduleConfigure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className JobScheduleConfigureDao
 * @date 2020/5/12 15:14
 **/
@Mapper
public interface JobScheduleConfigureDao extends BaseMapper<JobScheduleConfigure> {


    /**
     * 根据执行器id更新调度状态
     * @Author xiangtaohe
     * @Date  2020/5/12 17:14
     * @Version 1.0
     * @Param executorId
     * @Param state
     * @Return int
     **/
    @Update("UPDATE  job_schedule_configure SET state=#{state} WHERE executorId=#{executorId}")
    int updateState(@Param("executorId") String executorId, @Param("state") Integer state);
}
