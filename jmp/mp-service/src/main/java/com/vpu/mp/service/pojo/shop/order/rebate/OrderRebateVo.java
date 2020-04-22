package com.vpu.mp.service.pojo.shop.order.rebate;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单返利
 * @author 王帅
 */
@Data
public class OrderRebateVo {
    private Integer rebateId;
    private String orderSn;
    private Integer goodsId;
    private Integer productId;
    private Integer recId;
    private Byte rebateLevel;
    private Integer rebateUserId;
    private BigDecimal rebatePercent;
    private BigDecimal rebateMoney;
    private BigDecimal totalRebateMoney;
    private Timestamp createTime;
    private Timestamp updateTime;
    private BigDecimal realRebateMoney;
    private String username;
    private String goodsName;
}
