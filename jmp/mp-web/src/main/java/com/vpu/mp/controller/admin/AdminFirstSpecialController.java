package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.firstspecial.*;
import com.vpu.mp.service.pojo.shop.market.firstspecial.validated.FirstSpecialAddValidatedGroup;
import com.vpu.mp.service.pojo.shop.market.firstspecial.validated.FirstSpecialUpdateValidatedGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 王兵兵
 * @create: 2019-08-16 15:07
 **/
@RestController
public class AdminFirstSpecialController extends AdminBaseController {
    /**
     * 首单特惠活动分页查询列表
     *
     */
    @PostMapping(value = "/api/admin/market/firstspecial/list")
    public JsonResult getFirstSpecialPageList(@RequestBody @Validated FirstSpecialPageListQueryParam param) {
        return success(shop().firstSpecial.getPageList(param));
    }

    /**
     *添加 首单特惠活动
     *
     */
    @PostMapping(value = "/api/admin/market/firstspecial/add")
    public JsonResult addFirstSpecial(@RequestBody @Validated({FirstSpecialAddValidatedGroup.class}) FirstSpecialAddParam param) {
        shop().firstSpecial.addFirstSpecial(param);
        return success();
    }
    /**
     *取单个首单特惠活动信息
     *
     */
    @PostMapping(value = "/api/admin/market/firstspecial/get")
    public JsonResult getFirstSpecialById(@RequestBody @Validated SimpleFirstSpecialParam param) {
        FirstSpecialVo firstSpecialVo = shop().firstSpecial.getFirstSpecialById(param.getId());
        if(firstSpecialVo != null) {
            return success(firstSpecialVo);
        }else {
            return fail();
        }
    }
    /**
     *更新 首单特惠活动
     *
     */
    @PostMapping(value = "/api/admin/market/firstspecial/update")
    public JsonResult updateFirstSpecial(@RequestBody @Validated({FirstSpecialUpdateValidatedGroup.class}) FirstSpecialUpdateParam param) {
        shop().firstSpecial.updateFirstSpecial(param);
        return success();
    }

    /**
     *删除 首单特惠活动
     *
     */
    @PostMapping(value = "/api/admin/market/firstspecial/del")
    public JsonResult delFirstSpecial(@RequestBody @Validated SimpleFirstSpecialParam param) {
        shop().firstSpecial.delFirstSpecial(param.getId());
        return success();
    }

    /**
     * 首单特惠订单列表
     *
     */
    @PostMapping(value = "/api/admin/market/firstspecial/order")
    public JsonResult getFirstSpecialOrderList(@RequestBody @Validated MarketOrderListParam param) {
        return success(shop().firstSpecial.getFirstSpecialOrderList(param));
    }
}
