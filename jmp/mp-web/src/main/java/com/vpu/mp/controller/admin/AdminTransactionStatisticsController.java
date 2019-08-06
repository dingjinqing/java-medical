package com.vpu.mp.controller.admin;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.transaction.GeographicalParam;
import com.vpu.mp.service.pojo.shop.overview.transaction.LabelAnalysisParam;

/**
 * @author liufei
 * @date 2019/7/31
 * @description
 */
@RestController
public class AdminTransactionStatisticsController extends AdminBaseController{

    /**
     * 地域分布
     */
    @PostMapping("/api/admin/transactionstatistics/geographical")
    public JsonResult geographical(@RequestBody GeographicalParam param){
        return success(shop().transactionService.geographical(param));
    }

    /**
     * 标签成交分析
     */
    @PostMapping("/api/admin/transactionstatistics/labelanalysis")
    public JsonResult labelAnalysis(@RequestBody @Validated LabelAnalysisParam param){
        return success(shop().transactionService.labelAnalysis(param));
    }

    @PostMapping("/api/admin/transactionstatistics/defaultOverview")
    public JsonResult defaultOverview(){
        return success();
    }

}
