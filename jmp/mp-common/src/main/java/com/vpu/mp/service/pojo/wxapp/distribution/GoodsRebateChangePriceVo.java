package com.vpu.mp.service.pojo.wxapp.distribution;

import com.vpu.mp.service.pojo.shop.coupon.CouponListVo;
import com.vpu.mp.service.pojo.wxapp.coupon.CouponPageDecorationVo;
import lombok.Data;

import java.util.List;

/**
 * @Author 常乐
 * @Date 2020-04-06
 */
@Data
public class GoodsRebateChangePriceVo {
    /**
     * 商品基本信息
     */
    private BaseGoodsVo goods;
    /**
     * 商品分销改价信息
     */
    private List<RebateGoodsCfgVo> rebatePrice;
    /**
     * 优惠券列表
     */
    private List<CouponPageDecorationVo> couponList;
}
