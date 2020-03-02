package com.vpu.mp.service.pojo.wxapp.member.card;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2020-03-02 17:10
 **/
@Setter
@Getter
public class UserCardVo {
    /**
     * member_card的ID
     */
    private Integer id;
    private String cardNo;
    private Timestamp expireTime;
    private BigDecimal money;
    private String bgColor;
    private String cardName;
    private BigDecimal discount;
}
