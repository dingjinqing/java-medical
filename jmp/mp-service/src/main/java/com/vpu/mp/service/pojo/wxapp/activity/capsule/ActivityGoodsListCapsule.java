package com.vpu.mp.service.pojo.wxapp.activity.capsule;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsLabelMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 小程序-商品列表信息存储对象
 *
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Getter
@Setter
public class ActivityGoodsListCapsule extends GoodsBaseCapsule {
    private Integer goodsId;
    private String goodsName;
    private Byte goodsType;
    private BigDecimal shopPrice;
    private BigDecimal marketPrice;
    private BigDecimal prdMaxPrice;
    /**
     * 商品最总显示价格和划线价格
     */
    private BigDecimal realPrice;
    private BigDecimal linePrice;

    /**
     * 评价数量
     */
    private Integer commentNum;
    /**
     * 关系最紧密的标签信息
     */
    private GoodsLabelMpVo goodsLabel;

    /**
     * 商品拥有的营销信息，由各个processor添加
     */
    private List<GoodsActivityBaseMp> activities = new ArrayList<>(2);

    public GoodsListMpVo convertToGoodsListMpVo() {
        GoodsListMpVo vo = new GoodsListMpVo();
        vo.setGoodsId(this.goodsId);
        vo.setGoodsName(this.goodsName);
        // 地址是相对路径
        vo.setGoodsImg(this.goodsImg);
        vo.setGoodsNumber(this.goodsNumber);
        vo.setGoodsSaleNum(this.goodsSaleNum + this.baseSale);
        vo.setCommentNum(this.commentNum);
        vo.setDefaultPrd(this.defaultPrd);
        vo.setShopPrice(this.shopPrice);
        vo.setLinePrice(this.linePrice);
        vo.setRealPrice(this.realPrice);
        vo.setLabel(this.goodsLabel);
        vo.setGoodsActivity(this.activities);
        Optional<GoodsActivityBaseMp> first = this.activities.stream().filter(x -> GoodsConstant.isNeedReturnActivity(x.getActivityType())).findFirst();
        if (first.isPresent()) {
            GoodsActivityBaseMp activity = first.get();
            vo.setActivityType(activity.getActivityType());
            vo.setActivityId(activity.getActivityId());
        }

        return vo;
    }
}
