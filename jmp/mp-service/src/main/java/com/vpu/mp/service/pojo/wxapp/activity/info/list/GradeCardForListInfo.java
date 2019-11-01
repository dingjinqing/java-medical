package com.vpu.mp.service.pojo.wxapp.activity.info.list;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;

/**
 * @author 李晓冰
 * @date 2019年10月31日
 */
public class GradeCardForListInfo extends ActivityForListInfo {
    public GradeCardForListInfo() {
        activityType = GoodsConstant.ACTIVITY_TYPE_MEMBER_GRADE;
    }
}
