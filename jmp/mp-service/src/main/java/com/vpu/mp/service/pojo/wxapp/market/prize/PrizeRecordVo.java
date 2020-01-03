package com.vpu.mp.service.pojo.wxapp.market.prize;

import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsMpVo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * 我的奖品
 * @author 孔德成
 * @date 2020/1/3 10:03
 */
@Getter
@Setter
public class PrizeRecordVo {

    private Integer   id;
    private Integer   activityId;
    private Integer   activityType;
    private Integer   prdId;
    private String    orderSn;
    private Byte      prizeStatus;
    private Integer   expiredDay;
    private Timestamp expiredTime;
    private Timestamp createTime;

    private OrderGoodsMpVo orderGoodsMpVo;

}
