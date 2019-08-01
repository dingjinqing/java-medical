package com.vpu.mp.controller.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.db.shop.tables.records.FreeShippingRecord;
import com.vpu.mp.db.shop.tables.records.FreeShippingRuleRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.freeshipping.*;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.FileChannel;
import java.util.List;

/**
 * 满包邮活动
 *
 * @author 孔德成
 * @date 2019/7/29 15:59
 */
@RestController
@RequestMapping("/api")
public class AdminFreeShippingController extends AdminBaseController {


    /**
     * 列表
     *
     * @return
     */
    @PostMapping("/admin/market/free/shipping/list")
    public JsonResult freeShippingList(@RequestBody  FreeShipQueryParam param) {
        PageResult<FreeShippingVo> freeShippingList = shop().freeShipping.getFreeShippingList(param);
        return success(freeShippingList);
    }

    /**
     * 获取满包邮详情
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/market/free/shipping/get")
    public JsonResult getFreeShipping(@RequestBody  FreeShipQueryParam param) {
        if (param.getId() == null) {
            return fail();
        }
        FreeShippingRecord freeShipping = shop().freeShipping.getFreeShippingById(param.getId());
        Result<FreeShippingRuleRecord> freeShippingRule = shop().freeShipping.ruleService.getRuleListByFreeShippingId(param.getId());
        FreeShippingVo freeShippingVo = freeShipping.into(FreeShippingVo.class);
        freeShippingVo.setRuleList(freeShippingRule.into(FreeShippingRuleVo.class));
        return success(freeShippingVo);
    }


    /**
     * 添加
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/market/free/shipping/add")
    public JsonResult addFreeShipping(@RequestBody FreeShippingParam param) {
        shop().freeShipping.addFreeShipping(param);
        return success();
    }

    /**
     * 修改
     *
     * @return
     */
    @PostMapping("/admin/market/free/shipping/update")
    public JsonResult updateFreeShipping(@RequestBody FreeShippingParam param) {
        if (param.getId() == null) {
            return fail();
        }
        shop().freeShipping.updateFreeShipping(param);
        return success();
    }

    /**
     * 改变状态
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/market/free/shipping/status/change")
    public JsonResult changeStatus(@RequestBody FreeShippingChangeParam param) {
        if (param.getId() == null) {
            return fail();
        }
        shop().freeShipping.changeStatus(param.getId());
        return success();
    }


    /**
     * 删除
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/market/free/shipping/delete")
    public JsonResult deleteFreeShipping(@RequestBody FreeShippingChangeParam param) {
        if (param.getId() == null) {
            return fail();
        }
        shop().freeShipping.deleteFreeShipping(param.getId());
        return success();
    }

}
