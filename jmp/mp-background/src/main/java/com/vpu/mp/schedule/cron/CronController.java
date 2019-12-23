package com.vpu.mp.schedule.cron;

import com.vpu.mp.service.pojo.saas.schedule.cron.CronDefineParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liufei
 * @date 12/21/19
 */
@RestController("/api/system/schedule")
public class CronController {
    @Autowired
    CronTaskRegistrar cronTaskRegistrar;

    @PostMapping("/enable")
    public String enableSchedule(@RequestBody @Validated CronDefineParam param) {
        cronTaskRegistrar.enableSchedule(param);
        return "启用成功！";
    }

    @PostMapping("/disable")
    public String disableSchedule(@RequestBody @Validated CronDefineParam param) {
        cronTaskRegistrar.disableSchedule(param);
        return "停用成功！";
    }

    @PostMapping("/execute")
    public String executeSchedule(@RequestBody @Validated CronDefineParam param) {
        cronTaskRegistrar.executeSchedule(param);
        return "执行成功！";
    }
}
