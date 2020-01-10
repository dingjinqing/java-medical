package com.vpu.mp.service.pojo.wxapp.order.marketing.coupon;

import com.vpu.mp.service.pojo.wxapp.order.marketing.base.BaseMarketingBaseVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class OrderCouponVo extends BaseMarketingBaseVo {
    private String recommendGoodsId;
    private String recommendCatId;
    private String recommendSortId;
    private String recommendProductId;
    /**
     * 记录ID
     */
    public Integer id;
    /**
     * 优惠券名称
     */
    public String actName;
    /**
     * 优惠券码
     */
    public String couponSn;
    /**
     * 有效期开始时间
     */
    public Timestamp startTime;
    /**
     * 有效期结束时间
     */
    public Timestamp endTime;
    /**
     * 优惠类型 0:减价;1打折
     */
    public Integer type;
    /**
     * 打折或减价量
     */
    public BigDecimal amount;
    /**
     * 使用条件
     */
    public BigDecimal limitOrderAmount;
    /**
     * 是否已使用 0 未使用 1 已使用 2过期吧 3 废除
     */
    public Integer isUsed;
    /**
     *使用限制
     */
    public Byte useConsumeRestrict;
}
