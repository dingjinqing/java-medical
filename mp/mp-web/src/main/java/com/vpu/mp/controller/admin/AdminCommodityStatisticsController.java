package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.overview.Tuple2;
import com.vpu.mp.service.pojo.shop.overview.commodity.ProductEffectParam;
import com.vpu.mp.service.pojo.shop.overview.commodity.ProductEffectVo;
import com.vpu.mp.service.pojo.shop.overview.commodity.ProductOverviewParam;
import com.vpu.mp.service.pojo.shop.overview.commodity.ProductOverviewVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Author:liufei
 * @Date:2019/7/19
 * @Description: 概览-商品统计
 */
@RestController
public class AdminCommodityStatisticsController extends AdminBaseController{

    /**
     * 商品概览
     * @param param
     * @return
     */
    @PostMapping("/api/admin/commoditystatistics/productoverview")
    public JsonResult productOverview(@RequestBody @Validated ProductOverviewParam param){
        ProductOverviewVo vo ;
        if (param.getBrandId() > 0 || param.getSortId() > 0 || param.getLabelId() > 0){
            if (param.getDynamicDate() > 0){
                formatDate(param);
            }
            vo = shop().statisticsService.conditionOverview(param);
        }else if(param.getDynamicDate() > 0){
            vo = shop().statisticsService.fixedDayOverview(param);
        }else {
            vo = shop().statisticsService.customizeDayOverview(param);
        }
        return success(vo);
    }
    public void formatDate(ProductOverviewParam param){
        byte dynamicDate = param.getDynamicDate();
        switch (dynamicDate){
            case 1 :
                param.setStartTime(Util.getEarlyTimeStamp(new Date(),-1));
                param.setEndTime(Util.getStartToday(new Date()));
                break;
            case 7 :
                param.setStartTime(Util.getEarlyTimeStamp(new Date(),-7));
                param.setEndTime(Util.getStartToday(new Date()));
            case 30 :
                param.setStartTime(Util.getEarlyTimeStamp(new Date(),-30));
                param.setEndTime(Util.getStartToday(new Date()));
                break;
            default :
                param.setStartTime(Util.getEarlyTimeStamp(new Date(),-1));
                param.setEndTime(Util.getStartToday(new Date()));
                break;
        }
    }

    /**
     * 商品效果
     * @param param
     * @return
     */
    @PostMapping("/api/admin/commoditystatistics/producteffect")
    public JsonResult productEffect(@RequestBody @Validated ProductEffectParam param){
        PageResult<ProductEffectVo> vo;
        if(param.getDynamicDate() > 0){
            vo = shop().statisticsService.fixedDayEffect(param);
        }else{
            vo = shop().statisticsService.customizeDayEffect(param);
        }
        return success(vo);
    }

    /**
     * 商品统计
     * @param param
     * @return
     */
    @PostMapping("/api/admin/commoditystatistics/defaultOverview")
    public JsonResult defaultOverview(@RequestBody @Validated ProductEffectParam param){
        return success(new Tuple2<>(productOverview(param).getContent(),productEffect(param).getContent()));
    }

    /**
     * 商品效果导出excel
     * @param param
     * @return
     */
    @PostMapping("/api/admin/commoditystatistics/export2Excel")
    public void export2Excel(@RequestBody @Validated ProductEffectParam param,HttpServletResponse response){
        try {
            Workbook workbook=shop().statisticsService.export2Excel(param);
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            String fileName = "商品效果" + System.currentTimeMillis() + ".xlsx";
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
