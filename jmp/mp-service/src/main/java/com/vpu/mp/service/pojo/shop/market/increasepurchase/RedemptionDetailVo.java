package com.vpu.mp.service.pojo.shop.market.increasepurchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author liufei
 * @date 2019/8/15
 * @description
 */
@Data
public class RedemptionDetailVo {
    /** 用户id */
    private Integer userId;
    /** 昵称 */
    private String username;
    /** 手机号 */
    private String mobile;
    /** 订单编号 */
    private String orderSn;
    /** 换购时间 */
    private Timestamp createTime;

    /** 辅助参数 */
    @JsonIgnore
    private String concatPrices;
    @JsonIgnore
    private String concatNumbers;
    @JsonIgnore
    private String activityRules;


    /** 主商品总金额 */
    private BigDecimal mainGoodsTotalMoney;
    /** 换购数量 */
    private Integer redemptionNum;
    /** 换购总金额 */
    private BigDecimal redemptionTotalMoney;

}
