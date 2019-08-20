package com.vpu.mp.service.pojo.shop.market.givegift.receive;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/8/19 12:03
 */
@Data
public class GiveGiftReceiveListVo {
    Integer receiveuserId;
    String receiveusername;
    String receivemobile;
    Integer giveruserId;
    String giverusername;
    String givermobile;
    Integer productId;
    String mainOrderSn;
    Timestamp createTime;
    String orderSn;
    Byte status;
    String statusName;
    Byte orderStatus;
    String orderStatusName;
    Byte giftType;
    String goodsName;
}
