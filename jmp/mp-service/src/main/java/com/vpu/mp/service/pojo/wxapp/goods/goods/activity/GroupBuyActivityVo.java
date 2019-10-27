package com.vpu.mp.service.pojo.wxapp.goods.goods.activity;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年10月23日
 * 小程序-拼团活动返回信息类 activityType=1
 */
public class GroupBuyActivityVo extends ActivityBaseVo{
    public GroupBuyActivityVo() {
        activityType = GoodsConstant.GOODS_TYPE_GROUP_BUY;
    }

    /**参与活动的商品的原始价格*/
    @Getter
    @Setter
    private BigDecimal originalPrice;
}
