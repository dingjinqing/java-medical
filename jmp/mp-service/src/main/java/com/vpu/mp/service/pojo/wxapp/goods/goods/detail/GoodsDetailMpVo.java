package com.vpu.mp.service.pojo.wxapp.goods.goods.detail;

import com.vpu.mp.service.pojo.shop.config.pledge.PledgeInfo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.promotion.PromotionBase;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年11月08日
 */
@Setter
@Getter
public class GoodsDetailMpVo extends GoodsBaseMp {

    //************ElasticSearch中的数据**************start
    /**图片集合*/
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
    /**-商品装修模板id*/
    private Integer goodsPageId;
    /**商品详情描述*/
    private String goodsDesc;
    /**商品广告词*/
    private String goodsAd;
    private Integer brandId;
    private String brandName;
    /**商品使用的运费模板id*/
    private Integer deliverTemplateId;
    /**商品发货地*/
    private String deliverPlace;
    /**商品重量*/
    private BigDecimal goodsWeight;

    /**商品规格信息*/
    List<GoodsPrdMpVo> products;
    //************ElasticSearch中的数据**************end

    /**用户是否可以购买本商品*/
    private Boolean userCanBuy;
    /**商品所关联的标签（包含通过catId,sortId，allId关联的）*/
    List<String> labels;
    /**商品购买时需要的运费，详情展示时的默认运费*/
    private BigDecimal deliverPrice;
    /**是否已删除*/
    private Byte delFlag;
    private Boolean isCollected;
    private CommentDetailVo comment;
    /**相关优惠券*/
    List<CouponDetailMpVo> coupons;
    /**商品专享会员卡*/
    List<MemberCardDetailMpVo> memberCards;
    /** 详情页所指定的营销活动 */
    private GoodsActivityBaseMp activity;

    /**商品促销活动列表*/
    List<? extends PromotionBase> promotions = new ArrayList<>();
    //**********服务承诺
    /**
     * 服务承诺是否开启
     */
    private Integer pledgeSwitch;
    /**
     * 服务承诺信息
     */
    private  List<PledgeInfo> pledgeList;

    @Override
    public String toString() {
        return "GoodsDetailMpVo{" +
            "goodsImgs=" + goodsImgs +
            ", goodsVideo='" + goodsVideo + '\'' +
            ", goodsVideoImg='" + goodsVideoImg + '\'' +
            ", goodsVideoSize=" + goodsVideoSize +
            ", videoWidth=" + videoWidth +
            ", videoHeight=" + videoHeight +
            ", limitBuyNum=" + limitBuyNum +
            ", limitMaxNum=" + limitMaxNum +
            ", isExclusive=" + isExclusive +
            ", isPageUp=" + isPageUp +
            ", goodsPageId=" + goodsPageId +
            ", goodsDesc='" + goodsDesc + '\'' +
            ", goodsAd='" + goodsAd + '\'' +
            ", brandId=" + brandId +
            ", brandName='" + brandName + '\'' +
            ", deliverTemplateId=" + deliverTemplateId +
            ", deliverPlace='" + deliverPlace + '\'' +
            ", goodsWeight=" + goodsWeight +
            ", products=" + products +
            ", userCanBuy=" + userCanBuy +
            ", labels=" + labels +
            ", deliverPrice=" + deliverPrice +
            ", delFlag=" + delFlag +
            ", isCollected=" + isCollected +
            ", comment=" + comment +
            ", coupons=" + coupons +
            ", memberCards=" + memberCards +
            ", activity=" + activity +
            ", pledgeSwitch=" + pledgeSwitch +
            ", pledgeList=" + pledgeList +
            '}'+super.toString();
    }
}
