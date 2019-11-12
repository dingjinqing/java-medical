package com.vpu.mp.service.pojo.wxapp.goods.goods.detail;

import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsBaseMpVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年11月08日
 */
@Setter
@Getter
public class GoodsDetailMpVo extends GoodsBaseMpVo {

    private List<String> goodsImgs;
    private String goodsVideo;
    private String goodsVideoImg;
    private Double goodsVideoSize;
    private Integer videoWidth;
    private Integer videoHeight;
    private Integer limitBuyNum;
    private Integer limitMaxNum;
    private Byte isExclusive;

    private Integer brandId;
    private String brandName;
    private Boolean userCanBuy;
    List<String> labels;
    List<CouponDetailMpVo> coupons;
    List<GoodsPrdMpVo> products;
    List<MemberCardDetailMpVo> memberCards;

    /** 详情页所指定的营销活动 */
    private GoodsActivityBaseMp activity;
}
