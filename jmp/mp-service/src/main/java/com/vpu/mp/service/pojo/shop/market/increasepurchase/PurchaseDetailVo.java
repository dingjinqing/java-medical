package com.vpu.mp.service.pojo.shop.market.increasepurchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author liufei
 * @date 2019/8/14
 * @description
 */
@Data
public class PurchaseDetailVo {
    private Integer id;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动起始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp startTime;
    /**
     * 活动结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp endTime;
    /**
     * 活动优先级
     */
    private Short level;
    /**
     * 活动信息规则，主商品购满 [] 元可加 [] 元换购
     */
    private BigDecimal[][] purchaseInfo;
    /**
     * 单笔最大换购数量
     */
    private Short maxChangePurchase;
    /**
     * 主商品id列表字符串
     */
    @JsonIgnore
    private String goodsId;

    /**
     * 主商品信息
     */
    private List<GoodsInfo> mainGoods;
    /**
     * 不同换购规则的加价购商品信息
     */
    private List<GoodsInfo>[] redemptionGoods;
}
