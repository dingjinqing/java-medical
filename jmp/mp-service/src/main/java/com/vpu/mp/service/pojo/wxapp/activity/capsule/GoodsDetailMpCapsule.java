package com.vpu.mp.service.pojo.wxapp.activity.capsule;

import com.vpu.mp.db.shop.tables.records.GradePrdRecord;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.CouponDetailMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsDetailMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.MemberCardDetailMpVo;
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

    private List<String> labels;
    private List<CouponDetailMpVo> coupons;
    private List<GoodsPrdMpVo> products;
    private List<GradePrdRecord> gradeCardPrice;
    /**当前用户是否可以购买该商品*/
    private Boolean userCanBuy;
    /**商品会员专享卡信息*/
    private List<MemberCardDetailMpVo> exclusiveCards;

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
        vo.setGoodsVideoSize(this.goodsVideoSize);
        vo.setVideoWidth(this.videoWidth);
        vo.setVideoHeight(this.videoHeight);
        vo.setLimitBuyNum(this.limitBuyNum);
        vo.setLimitMaxNum(this.limitMaxNum);
        vo.setIsExclusive(this.isExclusive);

        vo.setUserCanBuy(this.userCanBuy);
        vo.setLabels(this.labels);
        vo.setCoupons(coupons);
        vo.setProducts(this.products);
        vo.setMemberCards(exclusiveCards);
        return vo;
    }
}
