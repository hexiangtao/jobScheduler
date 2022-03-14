package com.ykn.jobscheduler.admin.controller;

import com.ykn.jobscheduler.admin.bo.JobConfigureSaveCmd;
import com.ykn.jobscheduler.admin.bo.JobQry;
import com.ykn.jobscheduler.admin.bo.ScheduleSaveCmd;
import com.ykn.jobscheduler.admin.model.JobConfigure;
import com.ykn.jobscheduler.admin.service.JobConfigService;
import com.ykn.jobscheduler.common.Return;
import com.ykn.jobscheduler.common.ThreadPoolInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: hexiangtao
 * @date: 2020/3/24 15:22
 */
@RestController
@RequestMapping("/console")
public class ConsoleController {

    static final String STR_MASK = "******";


    @Resource
    private JobConfigService jobConfigService;


    @PostMapping("/job/add")
    public Return<Long> addJob(@RequestBody JobConfigureSaveCmd configure) {
        Long id = jobConfigService.saveJobConfigure(configure);
        return Return.success(id);
    }

    @PostMapping("/job/schedule/config")
    public Return<Long> saveScheduleConfig(@RequestBody ScheduleSaveCmd request) {
        jobConfigService.saveScheduleConfigure(request);
        return Return.success(request.getJobId());
    }


    @GetMapping("/job/list")
    public Return<List<JobConfigure>> listJob(JobQry request) {
        List<JobConfigure> list = jobConfigService.listAllJob(request);
        return Return.success(list);
    }


    @RequestMapping("job/status/update")
    public Return<String> updateStatus(@RequestParam("id") Long id, @RequestParam("status") int status) {
        jobConfigService.updateJobStatus(id, status);
        return Return.success("");
    }


    @GetMapping("scheduler/info")
    public Return<ThreadPoolInfo> getThreadPoolInfo(@RequestParam("executorId") String executorId) {
        return Return.success(JobConfigService.getThreadPoolInfo());
    }
}
