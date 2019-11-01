package com.vpu.mp.service.pojo.wxapp.activity.info.list;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
public class GroupBuyForListInfo extends ActivityForListInfo {
    public GroupBuyForListInfo() {
        super();
        activityType = GoodsConstant.ACTIVITY_TYPE_GROUP_BUY;
    }
}
