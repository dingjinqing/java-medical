package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.realtime.RealTimeVo;
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
        RealTimeVo realTimeVo = shop().realTimeOverview.realTime();
        return realTimeVo != null ? success(realTimeVo) : fail();
    }
}
