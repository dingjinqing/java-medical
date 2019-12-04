package com.vpu.mp.controller.admin;

import com.vpu.mp.service.pojo.shop.config.pledge.group.UpdateGroup;
import org.jooq.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.FreeShippingRecord;
import com.vpu.mp.db.shop.tables.records.FreeShippingRuleRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShipQueryParam;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingChangeParam;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingParam;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingRuleVo;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingVo;

import javax.validation.Valid;

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
    public JsonResult freeShippingList(@RequestBody @Valid FreeShipQueryParam param) {
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
    public JsonResult getFreeShipping(@RequestBody @Valid  FreeShippingChangeParam param) {
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
    public JsonResult addFreeShipping(@RequestBody @Valid FreeShippingParam param) {
        shop().freeShipping.addFreeShipping(param);
        return success();
    }

    /**
     * 修改
     *
     * @return
     */
    @PostMapping("/admin/market/free/shipping/update")
    public JsonResult updateFreeShipping(@RequestBody @Validated(UpdateGroup.class) FreeShippingParam param) {
        if (param.getId() == null) {
            return fail();
        }
        shop().freeShipping.updateFreeShipping(param);
        return success();
    }

    @PostMapping("/admin/market/free/shipping/share")
    public JsonResult shareFreeShipping(@RequestBody @Valid FreeShippingChangeParam param){
        return success(shop().freeShipping.shareFreeShipping(param.getId()));
    }

    /**
     * 改变状态
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/market/free/shipping/status/change")
    public JsonResult changeStatus(@RequestBody @Valid FreeShippingChangeParam param) {
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
