package com.vpu.mp.service.pojo.shop.market.presale;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * 定金膨胀活动入参
 *
 * @author 郑保乐
 */
@Data
public class PreSaleParam {

    /** 定金膨胀 **/
    public static final byte PRESALE = 0;
    /** 全款购买 **/
    public static final byte FULL = 1;
    /** 默认样式 **/
    public static final byte STYLE_DEFAULT = 0;
    /** 自定义样式 **/
    public static final byte STYLE_CUSTOMIZE = 1;
    /** 分享活动商品图 **/
    public static final byte SHARE_GOODS_IMG = 0;
    /** 分享自定义图片 **/
    public static final byte SHARE_CUSTOMIZE_IMG = 1;
    /** 指定时间发货 **/
    public static final byte DELIVER_SPECIFIC = 1;
    /** 尾款支付完成几天后发货 **/
    public static final byte DELIVER_POSTPONE = 2;
    /** 优惠不可叠加 **/
    public static final byte DISCOUNT_NO_SUPERIMPOSED = 0;
    /** 优惠可叠加 **/
    public static final byte DISCOUNT_SUPERIMPOSED = 1;
    /** 预售数量展示 **/
    public static final byte PRESALE_NUM_SHOW = 1;
    /** 预售数量不展示 **/
    public static final byte PRESALE_NUM_HIDE = 0;
    /** 支持原价购买 **/
    public static final byte ORIGINAL_PRICE_BUY_ENABLE = 1;
    /** 不支持原价购买 **/
    public static final byte ORIGINAL_PRICE_BUY_DISABLE = 0;
    /** 自动退定金 **/
    public static final byte AUTO_RETURN_PRESALE_MONEY = 1;
    /** 不退定金 **/
    public static final byte NOT_RETURN_PRESALE_MONEY = 0;

    /** 活动id **/
    private Integer id;
    /** 活动类型 **/
    @NotNull
    private Byte presaleType;
    /** 活动名称 **/
    @NotNull
    private String presaleName;
    /** 定金期数 **/
    @NotNull
    private Byte prePayStep;
    /** 定金支付开始时间 **/
    @NotNull
    private Timestamp preStartTime;
    /** 定金支付结束时间 **/
    @NotNull
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
    @NotNull
    private Integer goodsId;
    /** 发货时间模式 **/
    @NotNull
    private Byte deliverType;
    /** 指定发货时间 **/
    private Timestamp deliverTime;
    /** 尾款支付几天后发货 **/
    private Integer deliverDays;
    /** 优惠叠加策略 **/
    @NotNull
    private Byte discountType;
    /** 定金退款策略 **/
    @NotNull
    private Byte returnType;
    /** 预售数量展示 **/
    @NotNull
    private Integer showSaleNumber;
    /** 商品购买方式 **/
    @NotNull
    private Byte buyType;
    /** 最大购买数量 **/
    @NotNull
    private Integer buyNumber;
    /** 分享样式 **/
    @NotNull
    private Byte shareAction;
    /** 分享文案 **/
    private String shareDoc;
    /** 分享图来源 **/
    private Byte shareImgAction;
    /** 自定义图片 **/
    private String shareImg;
    /** 活动商品 **/
    @NotEmpty
    private List<ProductParam> products;
}
