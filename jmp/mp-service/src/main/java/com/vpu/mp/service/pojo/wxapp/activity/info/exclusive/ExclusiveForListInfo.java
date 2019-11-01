package com.vpu.mp.service.pojo.wxapp.activity.info.exclusive;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会员专享信息
 * @author 李晓冰
 * @date 2019年10月31日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExclusiveForListInfo extends ActivityForListInfo {
    public ExclusiveForListInfo() {
        activityType = GoodsConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE;
    }

    public ExclusiveForListInfo(Integer gctaId,Byte type){
        this();
        this.gctaId = gctaId;
        this.type = type;
    }

    private Integer gctaId;
    private Byte type;
}
