package com.vpu.mp.service.pojo.shop.market.bargain;

import com.vpu.mp.service.pojo.shop.config.PictorialShareConfigVo;
import com.vpu.mp.service.pojo.shop.coupon.CouponView;
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
    private Double     bargainMin;
    private Double     bargainMax;

    private Byte       bargainType;
    private Byte       bargainMoneyType;
    private BigDecimal bargainFixedMoney;
    private BigDecimal bargainMinMoney;
    private BigDecimal bargainMaxMoney;
    private Byte       freeFreight;
    private Byte needBindMobile;
    private Integer initialSales;
    private Integer first;

    /**
     * 砍价商品
     */
    private List<BargainGoodsUpdateVo> bargainGoods;

    private String     mrkingVoucherId;
    private List<CouponView> mrkingVoucherList;
    private String     rewardCouponId;
    private List<CouponView> rewardCouponList;

    private String shareConfig;
    private PictorialShareConfigVo shopShareConfig;
}
