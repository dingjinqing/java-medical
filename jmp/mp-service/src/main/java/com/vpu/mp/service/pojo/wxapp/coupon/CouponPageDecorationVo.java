package com.vpu.mp.service.pojo.wxapp.coupon;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2019-11-19 18:14
 **/
@Getter
@Setter
public class CouponPageDecorationVo {
    /**
     * 优惠券状态：
     *-1已领取或领取达到极限 1正常可用 2过期 3已抢光 4停用
     */
    private Byte status = 1;

    private Integer    id;
    private String     actCode;
    private String     actName;
    private Timestamp  startTime;
    private Timestamp  endTime;
    private BigDecimal denomination;
    private Integer    totalAmount;
    private Byte       type;
    private Integer    surplus;
    private Byte       useConsumeRestrict;
    private BigDecimal leastConsume;
    private String     useExplain;
    private Byte       enabled;
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
    private Integer    validity;
    private String     identityId;
    private String     recommendProductId;
    private Byte       useScore;
    private Integer    scoreNumber;
    private String     cardId;
    private Timestamp createTime;
    private Timestamp  updateTime;
    private Byte       validityType;
    private Integer    validityHour;
    private Integer    validityMinute;
    private Timestamp  expirationDate;
    private Byte       limitSurplusFlag;
    private Integer    randomMin;
    private Integer    randomMax;
    private Short      receivePerNum;
    private Integer    receiveNum;
}
