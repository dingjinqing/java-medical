package com.vpu.mp.service.pojo.wxapp.goods.goods;

import com.vpu.mp.service.pojo.wxapp.coupon.CouponTagVo;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月15日
 * 商品价格处理数据传输类，后期会将商品web接口按照小程序进行统一
 */
@Data
public class GoodsT {
    /**
     * 商品基础信息
     */
    private Integer goodsId;
    private String goodsName;
    private Integer catId;
    private Integer sortId;
    private String goodsImg;
    private Byte delFlag;
    private Byte isOnSale;
    private Integer goodsNumber;
    private Timestamp createTime;
    /**
     * 是否是默认规格
     */
    private Byte defaultPrd;

    /**
     * 商品售价，划线价，销量，评论相关字段
     */
    private BigDecimal shopPrice;
    private BigDecimal marketPrice;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private String linePriceStr;
    private Integer linePriceLen;
    private String realPriceStr;
    private Integer realPriceLen;
    private Integer goodsSaleNum;
    private Integer baseSale;
    private Integer saleLen;
    private String commentNumStr;
    private Integer commentLen;
    /**
     * 商品关联最近的标签
     */
    private GoodsLabelMpVo label;
    /**
     * 根据商品的活动类型中文名
     */
    private Byte goodsType;
    private List<Byte> goodsTags;
    private CouponTagVo couponTagVo;
}
