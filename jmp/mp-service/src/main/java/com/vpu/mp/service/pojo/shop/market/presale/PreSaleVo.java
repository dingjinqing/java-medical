package com.vpu.mp.service.pojo.shop.market.presale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 定金膨胀活动入参
 *
 * @author 郑保乐
 */
@Data
public class PreSaleVo implements StatusContainer {

    /** 活动id **/
    private Integer id;
    /** 活动类型 **/
    private Byte presaleType;
    /** 活动名称 **/
    private String presaleName;
    /** 定金期数 **/
    private Byte prePayStep;
    /** 定金支付开始时间 **/
    private Timestamp preStartTime;
    /** 定金支付结束时间 **/
    private Timestamp preEndTime;
    /** 2段定金支付开始时间 **/
    private Timestamp preStartTime2;
    /** 2段定金支付结束时间 **/
    private Timestamp preEndTime2;
    /** 尾款支付开始时间 **/
    private Timestamp startTime;
    /** 尾款支付结束时间 **/
    private Timestamp endTime;
    /** 商品id **/
    private Integer goodsId;
    /** 商品名称 **/
    private String goodsName;
    /** 发货时间模式 **/
    private Byte deliverType;
    /** 指定发货时间 **/
    private Timestamp deliverTime;
    /** 尾款支付几天后发货 **/
    private Integer deliverDays;
    /** 优惠叠加策略 **/
    private Byte discountType;
    /** 定金退款策略 **/
    private Byte returnType;
    /** 预售数量展示 **/
    private Byte showSaleNumber;
    /** 商品购买方式 **/
    private Byte buyType;
    /** 最大购买数量 **/
    private Integer buyNumber;
    /** 活动商品 **/
    private List<ProductVo> products;
    /** 分享配置 **/
    private Byte shareAction;
    private String shareDoc;
    private Byte shareImgAction;
    private String shareImg;
    /** 活动状态 **/
    private Byte status;

    @JsonIgnore
    private Integer saleNumber;
    @JsonIgnore
    private String shareConfig;
    @JsonIgnore
    private Byte delFlag;
    @JsonIgnore
    private Timestamp createTime;
    @JsonIgnore
    private Timestamp updateTime;
}
