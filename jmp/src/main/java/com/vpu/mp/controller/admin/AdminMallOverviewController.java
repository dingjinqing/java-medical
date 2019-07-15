package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.overview.BindUnBindOfficialParam;
import com.vpu.mp.service.pojo.shop.overview.DataDemonstrationParam;
import com.vpu.mp.service.pojo.shop.overview.DataDemonstrationVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:liufei
 * @Date:2019/7/15
 * @Description: 商城概览
 */
@RestController
public class AdminMallOverviewController extends AdminBaseController {

    /**
     * 数据展示
     * @param param
     * @return
     */
    @PostMapping("/api/admin/malloverview/datademonstration")
    public JsonResult dataDemonstration(@RequestBody @Validated DataDemonstrationParam param) {
        DataDemonstrationVo vo = shop().mallOverview.dataDemonstration(param);
        return vo != null ? success(vo) : fail(JsonResultMessage.OVERVIEW_MALL_DATADEMONSTRATION_GET_FAILED);
    }

    /**
     * 绑定解绑
     * @param param
     * @return
     */
    @PostMapping("/api/admin/malloverview/bindUnBindOfficial")
    public JsonResult bindUnBindOfficial(@RequestBody @Validated BindUnBindOfficialParam param){
        return saas.overviewService.bindUnBindOfficial(param) > 0 ? success() : fail(JsonResultMessage.OVERVIEW_MALL_BING_UNBING_FAILED);
    }
}
