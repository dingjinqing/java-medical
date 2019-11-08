package com.vpu.mp.service.pojo.wxapp.activity.capsule;

import com.vpu.mp.service.pojo.wxapp.activity.info.*;
import com.vpu.mp.service.pojo.wxapp.goods.goods.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 小程序详情页面信息存储对象
 * @author 李晓冰
 * @date 2019年11月07日
 */
@Getter
@Setter
public class GoodsDetailMpCapsule extends GoodsBaseCapsule{
    /**
     * 商品图片集合
     */
    private List<String> goodsImgs = new ArrayList<>();

    /**上下架状态*/
    private Byte isOnSale;
    /**是否已删除*/
    private Byte delFlag;

    private Integer goodsVideoId;
    private String goodsVideo;
    private String goodsVideoImg;
    private Double goodsVideoSize;
    private Integer videoWidth;
    private Integer videoHeight;

    private String brandName;
    private Integer limitBuyNum;
    private Integer limitMaxNum;
    private Byte isExclusive;

    private List<GoodsLabelProcessorDataInfo> labels;
    private List<CouponProcessorDataInfo> coupons;
    private List<GoodsPrdProcessorDataInfo> products;
    private List<GradeCardProcessorDataInfo> gradeCardPrice;
    /**当前用户是否可以购买该商品*/
    private Boolean userCanBuy;
    /**商品会员专享卡信息*/
    private List<ExclusiveProcessorDataInfo> exclusiveCards;

    public GoodsDetailMpVo convertToGoodsDetailMpVo(){
        GoodsDetailMpVo vo =new GoodsDetailMpVo();
        vo.setGoodsId(this.goodsId);
        vo.setGoodsName(this.goodsName);
        vo.setGoodsType(this.goodsType);
        vo.setGoodsSaleNum(this.goodsSaleNum);
        vo.setGoodsNumber(this.goodsNumber);
        vo.setBrandId(this.brandId);
        vo.setBrandName(this.brandName);
        vo.setDefaultPrd(this.defaultPrd);
        vo.setGoodsImgs(this.goodsImgs);
        vo.setGoodsVideo(this.goodsVideo);
        vo.setGoodsVideoImg(this.goodsVideoImg);
        vo.setVideoWidth(this.videoWidth);
        vo.setVideoHeight(this.videoHeight);
        vo.setUserCanBuy(this.userCanBuy);

        if (this.labels != null) {
            List<GoodsLabelMpVo> labels = new ArrayList<>();
            this.labels.forEach(label-> labels.add(label.convertToGoodsLabelMpVo()));
            vo.setLabels(labels);
        }

        if (this.coupons != null) {
            List<CouponMpVo> couponMpVos = new ArrayList<>();
            this.coupons.forEach(coupon-> couponMpVos.add(coupon.convertToCouponMpVo()));
            vo.setCoupons(couponMpVos);
        }

        if (this.products != null) {
            List<GoodsPrdMpVo> prdMpVos = new ArrayList<>();
            this.products.forEach(prd-> prdMpVos.add(prd.convertToGoodsPrdMpVo()));
            vo.setProducts(prdMpVos);
        }

        if (this.exclusiveCards != null) {
            List<MemberCardMpVo> cardVos =new ArrayList<>();
            this.exclusiveCards.forEach(card->cardVos.add(card.convertToMemberCardMpVo()));
            vo.setMemberCards(cardVos);
        }


        return vo;
    }
}
