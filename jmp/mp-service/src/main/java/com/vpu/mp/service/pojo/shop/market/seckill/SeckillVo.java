package com.vpu.mp.service.pojo.shop.market.seckill;

import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.shop.member.card.SimpleMemberCardVo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author: 王兵兵
 * @create: 2019-08-06 17:01
 **/
@Data
public class SeckillVo {
    /** 活动id*/
    private Integer skId;

    /** 活动名称*/
    private String name;

    /** 商品信息*/
    private List<SeckillGoods> goods;

    /** 开始时间*/
    private Timestamp startTime;

    /** 结束时间*/
    private Timestamp endTime;

    /** 每人限购数量*/
    private Short limitAmount;

    /** 秒杀当前库存*/
    private Integer stock;

    /** 规定的有效支付时间 单位：分钟*/
    private Short limitPaytime;

    /** 是否免运费： 1：免运费  0： 原先商品的运费*/
    private Byte freeFreight;

    /** 专属会员卡，卡ID字符串，逗号分隔；为空时代表该活动所有人都可以参与*/
    private List<SimpleMemberCardVo> memberCard;

    /** 分享设置*/
    private ShopShareConfig shopShareConfig;

    @Setter
    @Getter
    public static class SeckillGoods{
        private Integer goodsId;
        private String goodsName;
        /** 商品主图 */
        private String goodsImg;
        /** 商品库存 */
        private Integer goodsNumber;

        /** 商品价格 */
        private BigDecimal shopPrice;
        /** 单位 */
        private String unit;
        /** 秒杀商品规格价格设置实体*/
        private List<SecKillProductVo> secKillProduct;
    }
}
