package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.form.FormSearchParam;
import com.vpu.mp.service.pojo.shop.market.form.FormUAParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liufei
 * @date 2019/8/7
 * @description
 */
@RestController
public class AdminFormStatisticsController extends AdminBaseController {
    /**
     * 分页查询表单信息
     *
     * @param param 筛选条件
     * @return 响应分页数据集
     */
    @PostMapping("/api/admin/formstatistics/selectforminfo")
    public JsonResult selectFormInfo(@RequestBody @Validated FormSearchParam param) {
        return success(shop().formService.selectFormInfo(param));
    }

    /**
     * 添加表单
     *
     * @param param 表单信息入参
     */
    @PostMapping("/api/admin/formstatistics/addforminfo")
    public JsonResult addFormInfo(@RequestBody @Validated FormUAParam param) {
        shop().formService.addFormInfo(param);
        return success();
    }

    /**
     * 更新表单
     *
     * @param param 表单信息入参
     */
    @PostMapping("/api/admin/formstatistics/updateforminfo")
    public JsonResult updateFormInfo(@RequestBody @Validated FormUAParam param) {
        shop().formService.updateFormInfo(param);
        return success();
    }
}