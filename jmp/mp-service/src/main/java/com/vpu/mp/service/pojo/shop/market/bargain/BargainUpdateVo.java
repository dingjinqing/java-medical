package com.vpu.mp.service.pojo.shop.market.bargain;

import com.vpu.mp.service.pojo.shop.config.PictorialShareConfigVo;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.shop.coupon.CouponView;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsView;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author: 王兵兵
 * @create: 2019-09-04 18:04
 **/
@Data
public class BargainUpdateVo {
    private Integer    id;
    private String     bargainName;
    private Timestamp startTime;
    private Timestamp  endTime;
    private Integer    expectationNumber;
    private BigDecimal expectationPrice;
    private Double     bargainMin;
    private Double     bargainMax;
    private Integer    stock;

    private Byte       bargainType;
    private BigDecimal floorPrice;
    private Byte       bargainMoneyType;
    private BigDecimal bargainFixedMoney;
    private BigDecimal bargainMinMoney;
    private BigDecimal bargainMaxMoney;
    private Byte       freeFreight;

    private Integer    goodsId;
    private GoodsView goods;

    private String     mrkingVoucherId;
    private List<CouponView> mrkingVoucherList;
    private String     rewardCouponId;
    private List<CouponView> rewardCouponList;

    private String shareConfig;
    private PictorialShareConfigVo shopShareConfig;
}
