package com.vpu.mp.controller.system;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.saas.order.MainInquiryOrderStatisticsParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangpengcheng
 * @date 2020/8/14
 **/
@RestController
public class SystemInquiryOrderController  extends SystemBaseController{

    /**
     * 咨询订单统计报表
     * @param param
     * @return
     */
    @PostMapping("/api/system/inquiry/order/statistics")
    public JsonResult list(@RequestBody MainInquiryOrderStatisticsParam param){
        return success(saas.mainInquiryOrderService.orderStatistics(param));
    }
}
