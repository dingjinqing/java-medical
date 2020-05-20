package com.vpu.mp.service.pojo.shop.market.collect;

import com.vpu.mp.service.pojo.shop.coupon.CouponParamVo;
import lombok.Data;

import java.util.List;


/**
 * 收藏有礼-详情
 * @author liangchen
 * @date 2020.05.20
 */
@Data
public class CollectGiftVo extends CollectGiftParam {
    private List<CouponParamVo> couponDetail;
}
