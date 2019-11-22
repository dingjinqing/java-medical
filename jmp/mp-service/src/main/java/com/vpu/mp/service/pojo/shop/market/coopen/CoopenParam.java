package com.vpu.mp.service.pojo.shop.market.coopen;

import com.vpu.mp.service.pojo.shop.config.pledge.group.UpdateGroup;
import com.vpu.mp.service.pojo.wxapp.store.StorePayOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *  开屏有礼 添加
 * @author 孔德成
 * @date 2019/11/22 14:40
 */
@Getter
@Setter
public class CoopenParam implements Default {

    /** 新用户 **/
    public static final byte BRAND_NEW = 1;
    /** 全部用户 **/
    public static final byte ALL = 2;
    /** 未支付过的用户 **/
    public static final byte NOT_PAYED = 3;

    @NotNull(groups = {UpdateGroup.class})
    private Integer    id;
    /**
     * 针对用户
     */
    @NonNull
    private Byte       action;
    /**
     * 名称名称
     */
    @Length(max = 10)
    @NotBlank
    private String     name;
    /**
     * 标题
     */
    @Length(max = 20)
    private String     title;
    /**
     * 是否永久有效
     */
    private Integer    isForever;
    private Timestamp startDate;
    private Timestamp  endDate;
    /**
     * 优先级
     */
    private Integer    first;
    /**
     * 活动类型
     */
    @NotNull
    private Byte       activityAction;
    /**
     *  优惠卷
     */
    private String     mrkingVoucherId;
    /**
     * 抽奖id
     */
    private Integer    lotteryId;
    /**
     * 自定义链接
     */
    private String     customizeImgPath;
    /**
     * 自定义图片
     */
    private String     customizeUrl;
    /**
     * 积分
     */
    private BigDecimal giveScore;
    /**
     * 金额
     */
    private BigDecimal giveAccount;
    /**
     * 礼物数量
     */
    private Integer    awardNum;

}
