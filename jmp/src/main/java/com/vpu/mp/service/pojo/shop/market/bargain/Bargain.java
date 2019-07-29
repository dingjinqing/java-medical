package com.vpu.mp.service.pojo.shop.market.bargain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.vpu.mp.service.pojo.shop.goods.GoodsView;

import lombok.Data;

@Data
public class Bargain {

	private Integer    id;
    private String     bargainName;
    private Integer    goodsId;
    private Timestamp  startTime;
    private Timestamp  endTime;
    private Integer    expectationNumber;
    private BigDecimal expectationPrice;
    private Double     bargainMin;
    private Double     bargainMax;
    private Integer    stock;
    private String     mrkingVoucherId;
    private Byte       status;
    private Timestamp  createTime;
    private String     rewardCouponId;
    private String     shareConfig;
    private Byte       bargainType;
    private BigDecimal floorPrice;
    private Byte       bargainMoneyType;
    private BigDecimal bargainFixedMoney;
    private BigDecimal bargainMinMoney;
    private BigDecimal bargainMaxMoney;
    private Byte       freeFreight;
    private GoodsView goods;
}
