package com.vpu.mp.service.pojo.wxapp.activity.capsule;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.CouponProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.GoodsLabelProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsLabelMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

/**
 * 小程序-商品列表信息存储对象
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Getter
@Setter
public class ActivityGoodsListCapsule extends GoodsBaseCapsule{
    private Integer goodsId;
    private String goodsName;
    private Byte goodsType;
    private BigDecimal shopPrice;
    private BigDecimal marketPrice;
    private BigDecimal prdMaxPrice;
    /**商品最总显示价格和划线价格*/
    private BigDecimal realPrice;
    private BigDecimal linePrice;

    /**评价数量*/
    private Integer commentNum;
    /**关系最紧密的标签信息*/
    GoodsLabelProcessorDataInfo goodsLabel;

    /** 商品拥有的营销信息，由各个processor添加 */
    private List<ProcessorDataInfo> activities = new ArrayList<>(2);

    public GoodsListMpVo convertToGoodsListMpVo(){
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
        if (this.goodsLabel != null) {
            vo.setLabel(new GoodsLabelMpVo(goodsLabel.getName(),goodsLabel.getListPattern()));
        }

        activities.forEach(processorDataInfo -> {
            Map<String, Object> map = new HashMap<>(3);
            map.put("activityId",processorDataInfo.getDataId());
            map.put("activityType",processorDataInfo.getDataType());

            if (GoodsConstant.ACTIVITY_TYPE_COUPON.equals(processorDataInfo.getDataType())) {
                CouponProcessorDataInfo info = (CouponProcessorDataInfo) processorDataInfo;
                map.put("actCode", info.getActCode());
                map.put("denomination",info.getDenomination());
                map.put("useConsumeRestrict",info.getUseConsumeRestrict());
                map.put("leastConsume",info.getLeastConsume());
            }
        });

        return vo;
    }
}
