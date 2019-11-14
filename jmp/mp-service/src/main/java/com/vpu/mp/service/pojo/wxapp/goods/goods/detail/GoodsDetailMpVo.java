package com.vpu.mp.service.pojo.wxapp.goods.goods.detail;

import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsBaseMp;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年11月08日
 */
@Setter
@Getter
public class GoodsDetailMpVo extends GoodsBaseMp {
    /**商品主图和幅图集合*/
    private List<String> goodsImgs = new ArrayList<>();
    private String goodsVideo;
    private String goodsVideoImg;
    private Double goodsVideoSize;
    private Integer videoWidth;
    private Integer videoHeight;
    /**商品最小购买数量*/
    private Integer limitBuyNum;
    /**商品最大购买数量*/
    private Integer limitMaxNum;
    /**是否专享商品*/
    private Byte isExclusive;
    /**商品自定义内容是否在商品详情上方*/
    private Byte isPageUp;
    /**商品详情描述*/
    private String goodsDesc;
    /**是否已删除*/
    private Byte delFlag;

    private Integer brandId;
    private String brandName;
    /**用户是否可以购买本商品*/
    private Boolean userCanBuy;
    List<String> labels;
    /**相关优惠券*/
    List<CouponDetailMpVo> coupons;
    /**商品规格信息*/
    List<GoodsPrdMpVo> products;
    /**商品专享会员卡*/
    List<MemberCardDetailMpVo> memberCards;

    /** 详情页所指定的营销活动 */
    private GoodsActivityBaseMp activity;
}
