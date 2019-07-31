package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.Tuple2;
import com.vpu.mp.service.pojo.shop.overview.realtime.CoreIndicatorParam;
import com.vpu.mp.service.pojo.shop.overview.realtime.CoreIndicatorVo;
import com.vpu.mp.service.pojo.shop.overview.realtime.RealTimeVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:liufei
 * @Date:2019/7/19
 * @Description:
 */
@RestController
public class AdminRealTimeOverviewController extends AdminBaseController {

    /**
     * 实时概况
     * @return
     */
    @PostMapping("/api/admin/realtimeoverview/realTime")
    public JsonResult realTime(@RequestBody @Validated CoreIndicatorParam param){
        RealTimeVo realTimeVo = shop().realTimeOverview.realTime();
        CoreIndicatorVo vo = shop().realTimeOverview.coreIndicator(param);
        Tuple2<RealTimeVo,CoreIndicatorVo> tuple = new Tuple2<>(realTimeVo,vo);
        return success(tuple);
    }

    /**
     * 核心指标
     * @return
     */
    @PostMapping("/api/admin/realtimeoverview/coreIndicator")
    public JsonResult coreIndicator(@RequestBody @Validated CoreIndicatorParam param){
        CoreIndicatorVo vo = shop().realTimeOverview.coreIndicator(param);
        return vo !=null ? success(vo) : fail();
    }
}
