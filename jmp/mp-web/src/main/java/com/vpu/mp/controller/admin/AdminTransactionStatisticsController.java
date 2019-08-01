package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.realtime.CoreIndicatorParam;
import com.vpu.mp.service.pojo.shop.overview.transaction.GeographicalParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:liufei
 * @Date:2019/7/31
 * @Description:
 */
@RestController
public class AdminTransactionStatisticsController extends AdminBaseController{

    @PostMapping("/api/admin/transactionstatistics/geographical")
    public JsonResult geographical(@RequestBody GeographicalParam param){
        return success(shop().transactionService.geographical(param));
    }

    @PostMapping("/api/admin/transactionstatistics/labelanalysis")
    public JsonResult labelAnalysis(@RequestBody @Validated CoreIndicatorParam param){
        return success();
    }

    @PostMapping("/api/admin/transactionstatistics/defaultOverview")
    public JsonResult defaultOverview(@RequestBody @Validated CoreIndicatorParam param){
        return success();
    }

}
