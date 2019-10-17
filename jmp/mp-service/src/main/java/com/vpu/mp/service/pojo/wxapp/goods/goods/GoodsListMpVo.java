package com.vpu.mp.service.pojo.wxapp.goods.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月12日
 * 小程序和装修商品模块返回实体类
 */
@Data
public class GoodsListMpVo {
    private Integer goodsId;
    private String goodsName;
    private Timestamp createTime;
    private String goodsImg;
    private Integer goodsNumber;

    private String goodsSaleNum;
    private Integer saleLen;
    private Byte isOnSale;
    private Byte defaultPrd;

    /**1拼团 3砍价 5秒杀 6限时降价 10预售 100优惠券 101满减*/
    private List<Byte> goodsTags;
    private Byte goodsType;
    private Byte delFlag;

    private GoodsLabelMpVo label;

    private BigDecimal shopPrice;
    private String linePrice;
    private Integer linePriceLen;
    private String realPrice;
    private Integer realPriceLen;
    private BigDecimal marketPrice;
    private Integer commentLen;
    private String commentNum;
}
