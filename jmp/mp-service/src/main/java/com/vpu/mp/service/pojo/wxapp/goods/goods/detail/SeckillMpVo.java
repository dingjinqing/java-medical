package com.vpu.mp.service.pojo.wxapp.goods.goods.detail;

import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: 王兵兵
 * @create: 2019-11-12 11:35
 **/
@Getter
@Setter
public class SeckillMpVo extends GoodsActivityBaseMp {
    /** 0正常;1该活动不存在;2该活动已停用;3该活动未开始;4该活动已结束;5商品已抢光;6该用户已达到限购数量上限;7该秒杀为会员专属，该用户没有对应会员卡; */
    private Byte actState;

    /** 当前前秒杀剩余库存(各规格库存之和) */
    private Integer stock;

    /** 每人限购数量 */
    private Short limitAmount;

    /** 规定的有效支付时间(分钟数) */
    private Short limitPaytime;

    /** 活动开始时间 */
    private Timestamp startTime;

    /** 活动结束时间 */
    private Timestamp endTime;

    /** 专属会员卡ID串，逗号隔开 */
    private String cardId;

    /** 分享配置 */
    private ShopShareConfig shareConfig;

    /** 秒杀规格 */
    private List<SecKillPrdMpVo> actProducts;
}
