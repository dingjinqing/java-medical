package com.vpu.mp.service.pojo.wxapp.activity.info.groupbuy;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
public class ActivityGroupBuyListInfo extends ActivityForListInfo {
    public ActivityGroupBuyListInfo() {
        super();
        activityType = GoodsConstant.GOODS_TYPE_GROUP_BUY;
    }

    @Getter
    @Setter
    private BigDecimal originalMaxPrice;
}
