package com.vpu.mp.service.pojo.wxapp.coupon;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author: 王兵兵
 * @create: 2020-02-27 11:37
 **/
@Getter
@Setter
public class CouponPackActInfoVo {

    //活动配置信息
    private PackInfo packInfo;
    //活动下属优惠券
    private List<CouponPackVoucher> packList;
    //用户已购买或领取的礼包数量
    private Integer buyCount;

    @Setter
    @Getter
    public static class PackInfo{
        private Integer id;

        /** 礼包名称 */
        private String     packName;

        /** 单用户领取限制次数，0不限制 */
        private Integer    limitGetTimes;

        /** 总数量 */
        private Integer    totalAmount;

        /** 获取方式，0：现金购买，1：积分购买，2直接领取 */
        private Byte       accessMode;

        /** 价格（现金或积分，直接领取时该值为0） */
        private BigDecimal accessCost;

        /** 活动规则 */
        private String     actRule;
    }

    @Getter
    @Setter
    public static class CouponPackVoucher{
        //礼包配置内容
        /**主键 */
        private Integer id;
        /** 优惠券id */
        private Integer voucherId;
        /** 总数量 */
        private Integer totalAmount;
        /** 立即发放数量 */
        private Integer immediatelyGrantAmount;
        /** 每个时间单位间隔（1为无间隔） */
        private Integer timingEvery;
        /** 定时发放的时间单位，0：自然天，1：自然周，2自然月 */
        private Byte timingUnit;
        /** 定时发放的时间,周1-7，月1-31，自然天不填 */
        private Integer timingTime;
        /** 定时发放的数量 */
        private Integer timingAmount;

        //优惠券信息
        /**
         * 优惠类型 voucher：指定金额  discount：打折
         */
        private String     actCode;
        /**
         * 优惠券名称
         */
        private String     actName;
        /**
         * 生效时间
         */
        private Timestamp startTime;
        /**
         * 失效时间
         */
        private Timestamp  endTime;
        /**
         * 面值
         */
        private BigDecimal denomination;
        /**
         * 使用门槛，0:无门槛；1:满金额使用
         */
        private Byte       useConsumeRestrict;
        /**
         * 满多少使用
         */
        private BigDecimal    leastConsume;
        /**
         * 唯一活动代码
         */
        private String     aliasCode;
        /**
         * 指定商品可用
         */
        private String     recommendGoodsId;
        /**
         * 指定品牌可用
         */
        private String     recommendCatId;
        /**
         * 指定商家分类可用
         */
        private String     recommendSortId;
        /**
         * 优惠券有效期类型
         */
        private Byte    validityType;
        /**
         * 优惠券有效天数
         */
        private Integer    validity;
        /**
         * 优惠券有效小时
         */
        private Integer validityHour;
        /**
         * 优惠券有效分钟数
         */
        private Integer validityMinute;
        /**
         * 关联商品规格
         */
        private String     recommendProductId;
        /**
         * 分裂优惠卷随机金额最高
         */
        private BigDecimal randomMax;
        /**
         *是否所有商品可用
         */
        private Boolean isAllGoodsUse;

        //实时发放信息
        /**已经发到手的该类型优惠券数量 */
        private Integer grantCount;
    }

}
