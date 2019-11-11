package com.vpu.mp.service.pojo.wxapp.goods.goods;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年11月08日
 */
@Setter
@Getter
public class GoodsDetailMpVo extends GoodsBaseMpVo{

    private List<String> goodsImgs;
    private Integer goodsVideoId;
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
    List<GoodsLabelMpVo> labels;
    List<CouponMpVo> coupons;
    List<GoodsPrdMpVo> products;
    List<MemberCardMpVo> memberCards;
}
