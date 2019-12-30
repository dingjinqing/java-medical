package com.vpu.mp.service.pojo.wxapp.market.bargain;

import com.vpu.mp.service.pojo.shop.config.PictorialShareConfigVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 小程序砍价详情页出参
 * @author: 王兵兵
 * @create: 2019-12-27 17:12
 **/
@Getter
@Setter
public class BargainInfoVo {
    /**
     * 砍价发起记录的当前状态
     * 0可以砍价（别人的砍价） 11可以邀请砍价（自己的砍价） 1活动不存在 2砍价失败 3活动未开始 4或已结束
     * 5砍价成功 6商品已抢光 7可以邀请砍价（自己的砍价） 8可以再砍一刀X元（自己的砍价） 9我也要X元得好物 10已完成订单（自己的砍价）
     *
     */
    private Byte state;
    /**
     * 8 和 9 的X
     */
    private BigDecimal stateMoney;

    /**
     * 服务器时间戳
     */
    private Timestamp timestamp;
    /**
     * 目标价格
     */
    private BigDecimal bargainPrice;

    private BargainRecordInfo recordInfo;
    private PictorialShareConfigVo recordShareImg;
    private List<BargainUser> recordUserList;

    @Setter
    @Getter
    @ToString
    public static class BargainRecordInfo{
        private Integer    id;
        private Integer    userId;
        private Integer    bargainId;
        private Integer    goodsId;
        private Integer    prdId;
        private BigDecimal goodsPrice;
        private BigDecimal bargainMoney;
        private Integer    userNumber;
        private Byte       status;
        private Integer    isOrdered;
        private String     orderSn;
        private Timestamp createTime;

        private String userAvatar;
        private String goodsName;
        private String goodsImg;
        private BigDecimal prdPrice;
        private String prdDesc;
        private Integer prdNumber;

        private Byte bargainType;
        private Timestamp startTime;
        private Timestamp endTime;
        private BigDecimal expectationPrice;
        private BigDecimal floorPrice;
        private String shareConfig;
        private Integer stock;

        private String wxOpenid;
        private String username;

        private long remainingTime;
    }

    @Setter
    @Getter
    public static class BargainUser{
        private Integer    id;
        private Integer    recordId;
        private Integer    userId;
        private BigDecimal bargainMoney;

        private String username;
        private String wxOpenid;
        private String userAvatar;
    }
}
