package com.vpu.mp.service.pojo.wxapp.goods.goods.detail;

import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author 李晓冰
 * @date 2019年12月23日
 */
@Getter
@Setter
public class GoodsActivityDetailMp extends GoodsActivityBaseMp {

    /**
     * 活动状态码值，对应码值内容：
     * @see com.vpu.mp.service.foundation.data.BaseConstant#ACTIVITY_STATUS_CAN_USE
     */
    private Byte actState;

    /** 活动结束时间 */
    private Timestamp endTime;

    /** 活动距开始时间或结束时间剩余时间 */
    private Long remainTime;
}
