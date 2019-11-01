package com.vpu.mp.service.pojo.wxapp.activity.info.list;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;

/**
 * 小程序-商品列表-预售信息
 * @author 李晓冰
 * @date 2019年11月01日
 */
public class PreSaleForListInfo extends ActivityForListInfo {
    public PreSaleForListInfo() {
        super();
        activityType = GoodsConstant.ACTIVITY_TYPE_PRE_SALE;
    }
}
