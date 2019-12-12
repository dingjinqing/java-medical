package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Locale;

/**
 * author liufei
 * date 2019/7/19
 * 概览-商品统计
 */
@RestController
public class AdminCommodityStatisticsController extends AdminBaseController {

    /**
     * 商品概览
     *
     * @param param the param
     * @return json result
     */
    @PostMapping("/api/admin/commoditystatistics/productoverview")
    public JsonResult productOverview(@RequestBody @Validated ProductOverviewParam param) {
        ProductOverviewVo vo;
        if (param.getBrandId() > 0 || param.getSortId() > 0 || param.getLabelId() > 0) {
            if (param.getDynamicDate() > 0) {
                formatDate(param);
            }
            vo = shop().statisticsService.conditionOverview(param);
        } else if (param.getDynamicDate() > 0) {
            vo = shop().statisticsService.fixedDayOverview(param);
        } else {
            vo = shop().statisticsService.customizeDayOverview(param);
        }
        return success(vo);
    }

    /**
     * 将指定时间转化为自定义时间供后续统一处理
     */
    private void formatDate(ProductOverviewParam param) {
        param.setStartTime(Timestamp.valueOf(LocalDate.now().minusDays(param.getDynamicDate()).atStartOfDay()));
        param.setEndTime(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
    }

    /**
     * 商品效果
     *
     * @param param the param
     * @return json result
     */
    @PostMapping("/api/admin/commoditystatistics/producteffect")
    public JsonResult productEffect(@RequestBody @Validated ProductEffectParam param) {
        PageResult<ProductEffectVo> vo;
        if (param.getDynamicDate() > 0) {
            vo = shop().statisticsService.fixedDayEffect(param);
        } else {
            vo = shop().statisticsService.customizeDayEffect(param);
        }
        return success(vo);
    }

    /**
     * 商品统计综合接口
     *
     * @param param the param
     * @return json result
     */
    @PostMapping("/api/admin/commoditystatistics/defaultOverview")
    public JsonResult defaultOverview(@RequestBody @Validated ProductEffectParam param) {
        return success(new Tuple2<>(productOverview(param).getContent(), productEffect(param).getContent()));
    }

    /**
     * 商品效果导出excel
     *
     * @param param    the param
     * @param response the response
     * @return
     */
    @PostMapping("/api/admin/commoditystatistics/export2Excel")
    public void export2Excel(@RequestBody @Validated ProductEffectParam param, HttpServletResponse response) {
        try {
            Workbook workbook = shop().statisticsService.export2Excel(param);
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            String fileName = "商品效果" + System.currentTimeMillis() + ".xlsx";
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setLocale(Locale.ENGLISH);
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
