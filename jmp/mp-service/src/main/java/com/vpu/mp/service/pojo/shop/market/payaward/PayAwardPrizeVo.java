package com.vpu.mp.service.pojo.shop.market.payaward;

import com.vpu.mp.service.pojo.shop.coupon.CouponView;
import com.vpu.mp.service.pojo.shop.goods.spec.ProductSmallInfoVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/12/20 15:50
 */
@Getter
@Setter
public class PayAwardPrizeVo {
    /**
     * 奖励类型
     */
    private Byte giftType;
    /**
     * 是否已经发送
     */
    private Byte      status;
    /**
     * 幸运大抽奖
     */
    private Integer    lotteryId;
    /**
     * 账号金额
     */
    private BigDecimal account;
    /**
     * 积分数量
     */
    private Integer    scoreNumber;
    /**
     * 商品id
     */
    private Integer    productId;
    private ProductSmallInfoVo product;
    private Integer    keepDays;
    private String     customImage;
    private String     customLink;
    /**
     * 优惠卷
     */
    private List<CouponView> couponView;
}
