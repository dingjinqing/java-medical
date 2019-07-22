package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:liufei
 * @Date:2019/7/19
 * @Description: 概览-商品统计
 */
@RestController
public class AdminCommodityStatisticsController extends AdminBaseController{

    @PostMapping("/api/admin/commoditystatistics/productoverview")
    public JsonResult productOverview(){
        //TODO 商品概况
        return success();
    }

    @PostMapping("/api/admin/commoditystatistics/producteffect")
    public JsonResult productEffect(){
        //TODO 商品效果
        return success();
    }

}
