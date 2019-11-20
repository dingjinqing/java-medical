package com.vpu.mp.service.pojo.wxapp.member.card;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2019-11-20 14:49
 **/
@Getter
@Setter
public class MemberCardPageDecorationVo {
    /**
     * 会员卡状态：
     *未领取-1 已领取1 过期2  停用3
     */
    private Byte status = 1;

    private Byte activation;
    private String bgColor;
    private String bgImg;
    private Byte bgType;
    private String cardGrade;
    private Integer cardId;
    private String cardName;
    private Byte cardType;
    private Timestamp endTime;
    private Byte expireType;
    private Byte flag;
    private Byte isPay;
    private BigDecimal payFee;
    private Byte payType;
    private Integer receiveDay;
    private Byte dateType;
    private Integer limit;
    private Integer stock;

}
