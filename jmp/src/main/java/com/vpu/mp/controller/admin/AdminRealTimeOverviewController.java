package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:liufei
 * @Date:2019/7/19
 * @Description:
 */
@RestController
public class AdminRealTimeOverviewController extends AdminBaseController {

    @PostMapping("/api/admin/realtimeoverview/realTime")
    public JsonResult realTime(){
        shop().realTimeOverview.realTime();
        return success();
    }
}
