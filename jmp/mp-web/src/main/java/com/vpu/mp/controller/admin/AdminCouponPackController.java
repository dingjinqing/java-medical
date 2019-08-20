package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.couponpack.CouponPackAddParam;
import com.vpu.mp.service.pojo.shop.market.couponpack.CouponPackPageListQueryParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 王兵兵
 * @create: 2019-08-20 11:22
 **/
@RestController
public class AdminCouponPackController extends AdminBaseController {

    /**
     * 优惠券礼包活动分页查询列表
     *
     */
    @PostMapping(value = "/api/admin/market/couponpack/list")
    public JsonResult getCouponPackPageList(@RequestBody @Validated CouponPackPageListQueryParam param) {
        return success(shop().couponPack.getPageList(param));
    }

    /**
     *添加 优惠券礼包活动
     *
     */
    @PostMapping(value = "/api/admin/market/couponpack/add")
    public JsonResult addCouponPack(@RequestBody @Validated CouponPackAddParam param) {
        shop().couponPack.addCouponPack(param);
        return success();
    }
}
