package com.vpu.mp.service.pojo.shop.market.payreward;

import com.vpu.mp.service.pojo.shop.coupon.CouponParam;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/8/12 18:15
 */
@Data
public class PayRewardParam {

    /**
     * 分裂
     */
    public static final Byte TYPE_SPLIT=1;
    /**
     * 幸运大抽奖
     */
    public static final Byte TYPE_LOTTERY =2;
    /**
     * 普通
     */
    public static final Byte TYPE_NORMAL =3;
    /**
     * 其他
     */
    public static final Byte TYPE_OTHER =4;



    private Integer    id;
    private String     actName;
    private Timestamp  startTime;
    private Timestamp  endTime;
    private Byte       type;
    private BigDecimal denomination;
    private String     couponIds;
    private Short      everytimeAmount;
    private Integer    lotteryId;
    private Byte       recommendType;
    private String     recommendGoodsId;
    private String     recommendCatId;
    private String     recommendSortId;
    private String     imgUrl;
    private String     linkPath;
    private Byte       status;

    /**
     * 分裂优惠券
     */
    private CouponParam coupon;

}
