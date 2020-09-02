package com.vpu.mp.service.pojo.wxapp.goods.goods.detail;

import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 李晓冰
 * @date 2019年12月23日
 */
@Getter
@Setter
public class GoodsActivityDetailMp extends GoodsActivityBaseMp {

    /**
     * 活动状态码值，对应码值内容：
     * @see com.vpu.mp.common.foundation.data.BaseConstant#ACTIVITY_STATUS_CAN_USE
     */
    private Byte actState;

    /** 活动距开始时间或结束时间剩余时间 */
    private Long startTime;

    /** 活动距结束时间或结束时间剩余时间 */
    private Long endTime;
}
