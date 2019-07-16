package com.vpu.mp.service.pojo.shop.coupon;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 创建优惠券
 * @author 常乐
 * 2019年7月16日
 */
@Data
public class CouponParam {

    private Integer    id;
    private Integer    shopId;
    private String     actCode;
    private String     actName;
    private Timestamp  startTime;
    private Timestamp  endTime;
    private BigDecimal denomination;
    private Integer    totalAmount;
    private Integer    surplus;
    private Integer    remainAmount;
    private Byte       useConsumeRestrict;
    private Integer    leastConsume;
    private String     useExplain;
    private Byte       enabled;
    private Byte       isRandom;
    private Short      receivePerPerson;
    private Byte       suitGoods;
    private Byte       togetherUsed;
    private Byte       permitShare;
    private Byte       remindOwner;
    private Short      giveoutAmount;
    private Short      giveoutPerson;
    private Short      receiveAmount;
    private Short      receivePerson;
    private Short      usedAmount;
    private String     aliasCode;
    private String     validationCode;
    private String     recommendGoodsId;
    private String     recommendCatId;
    private String     recommendSortId;
    private Integer    validity;
    private Byte       delFlag;
    private Byte       action;
    private String     identityId;
    private String     recommendProductId;
    private Byte       useScore;
    private Integer    scoreNumber;
    private String     cardId;
    private Timestamp  createTime;
    private Timestamp  updateTime;
}
