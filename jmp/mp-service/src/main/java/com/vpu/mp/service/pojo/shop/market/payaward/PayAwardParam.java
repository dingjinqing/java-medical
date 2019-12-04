package com.vpu.mp.service.pojo.shop.market.payaward;

import com.vpu.mp.service.pojo.shop.coupon.CouponParam;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/8/12 18:15
 */
@Getter
@Setter
public class PayAwardParam {


    private Integer    id;
    @Length(max = 10)
    private String     activityNames;
    private Timestamp  startTime;
    private Timestamp  endTime;
    /**
     * 时间类型 0 定时 1永久
     */
    private Byte timeType;
    /**
     * 优先级
     */
    private Integer actFirst;
    /**
     *  商品范围类型
     */
    private Integer goodsAreaType;
    /**
     * 商品id
     */
    private String goodsIds;
    /**
     * 商品平台分类
     */
    private String goodsCatIds;
    /**
     * 商品商家分类
     */
    private String goodsSortIds;
    /**
     * 最少支付金额
     */
    private BigDecimal minPayMoney;
    /**
     * 每个用户参与次数
     */
    private Integer limitTimes;
    /**
     * 奖励内容
     */
    private List<PayAwardContentBo> awardList;

}
