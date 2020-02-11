package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/7/19 16:32
 */
@Data
public class GroupBuyEditParam {

    @NotNull
    private Integer id;
    /**
     * 活动名称
     */
    @NotNull
    private String name;
    /**
     * 有效期
     */
    private Timestamp startTime;
    private Timestamp endTime;
    /**
     * 最少购买数 0不限制
     * limit_buy_num
     */
    private Short limitBuyNum;
    /**
     * 最多购买数 0 不限制
     */
    private Short limitMaxNum;
    /**
     * 每人参团数量限制 0表示不限制
     */
    private Short joinLimit;
    /**
     * 每人开团数量限制  0表示不限制数量
     */
    private Short openLimit;
    /**
     * 默认成团 0 不默认 1 默认成团
     */
    private Byte isDefault;
    /**
     * 是否免运费 1 免运费 2 使用原商品运费模板
     */
    private Byte shippingType;
    /**
     * 拼团失败发放优惠券
     */
    private String rewardCouponId;
    /**
     * 初始化等级
     */
    private Integer level=0;
    /**
     * 初始化数量
     */
    private Integer beginNum=0;

    /**
     * 分享设置
     */
    private GroupBuyShareConfigParam share;
    private String shareConfig;
    /**
     * 产品规格配置
     */
    @Size(min = 1)
    private List<GroupBuyProductParam> product;
}
