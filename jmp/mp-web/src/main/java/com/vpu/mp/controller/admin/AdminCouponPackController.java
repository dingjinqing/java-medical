package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.couponpack.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author: 王兵兵
 * @create: 2019-08-20 11:22
 **/
@RestController
public class AdminCouponPackController extends AdminBaseController {

    private static final String LANGUAGE_TYPE_EXCEL= "excel";

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

    /**
     *删除 优惠券礼包活动
     *
     */
    @PostMapping(value = "/api/admin/market/couponpack/del")
    public JsonResult delCouponPack(@RequestBody @Validated SimpleCouponPackParam param) {
        shop().couponPack.delCouponPack(param.getId());
        return success();
    }

    /**
     *取单个优惠券礼包活动信息
     *
     */
    @PostMapping(value = "/api/admin/market/couponpack/get")
    public JsonResult getCouponPackById(@RequestBody @Validated SimpleCouponPackParam param) {
        CouponPackUpdateVo couponPackUpdateVo = shop().couponPack.getCouponPackById(param.getId());
        if(couponPackUpdateVo != null) {
            return success(couponPackUpdateVo);
        }else {
            return fail();
        }
    }

    /**
     *更新 优惠券礼包活动
     *
     */
    @PostMapping(value = "/api/admin/market/couponpack/update")
    public JsonResult updateCouponPack(@RequestBody @Validated CouponPackUpdateParam param) {
        shop().couponPack.updateCouponPack(param);
        return success();
    }

    /**
     * 优惠券礼包活动订单列表
     *
     */
    @PostMapping(value = "/api/admin/market/couponpack/order")
    public JsonResult getCouponPackOrderPageList(@RequestBody @Validated CouponPackOrderListQueryParam param) {
        return success(shop().couponPack.getCouponPackOrderPageList(param));
    }

    /**
     * 导出优惠券礼包活动订单列表
     *
     */
    @PostMapping(value = "/api/admin/market/couponpack/order/export")
    public void exportCouponPackOrderList(@RequestBody @Valid CouponPackOrderListQueryParam param, HttpServletResponse response) throws IOException {
        Workbook workbook =shop().couponPack.exportCouponPackOrderList(param,getLang());
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        String fileName = Util.translateMessage(getLang(), JsonResultMessage.COUPON_PACK_ORDER_FILENAME,LANGUAGE_TYPE_EXCEL) + DateUtil.getLocalDateTime().toString();
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        workbook.write(response.getOutputStream());
    }

}
