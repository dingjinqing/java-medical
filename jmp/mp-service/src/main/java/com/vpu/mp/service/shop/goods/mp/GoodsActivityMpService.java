package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.db.shop.tables.records.MrkingVoucherRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.coupon.CouponTagVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsT;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.market.fullcut.MrkingStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月15日
 * 商品活动信息处理类,可在此处引入各种活动service处理类
 */
@Service
public class GoodsActivityMpService extends ShopBaseService {

    @Autowired CouponService couponService;
    @Autowired MrkingStrategyService mrkingStrategyService;


    /**
     * 根据商品所处于的活动获取其能够展示的标签值,
     * 需要展示的标签有：1拼团 3砍价 5秒杀 6限时降价 10预售 120优惠券 121满减
     * @return
     */
    public void disposeGoodsActivityTags(GoodsT goodsT) {
        List<Byte> listTags = new ArrayList<>();
        Byte goodsType =goodsT.getGoodsType();
        if (isIn135610Activity(goodsType)) {
            listTags.add(goodsType);
        }
        CouponTagVo couponTagVo = getGoodsCouponInfoFirst(goodsT);

        boolean hasAct =mrkingStrategyService.getGoodsAct(goodsT.getGoodsId(),goodsT.getCatId(),goodsT.getSortId());

        if (couponTagVo != null) {
            listTags.add(GoodsConstant.ACTIVITY_TYPE_HAS_COUPON);
        }

        if (hasAct) {
            listTags.add(GoodsConstant.ACTIVITY_TYPE_HAS_FULL_REDUCTION);
        }
        if (listTags.size() > 2) {
            listTags = listTags.subList(0,2);
        }
        goodsT.setGoodsTags(listTags);
        if (listTags.contains(GoodsConstant.ACTIVITY_TYPE_HAS_COUPON)) {
            goodsT.setCouponTagVo(couponTagVo);
        }
    }

    /**
     * 获取商品最紧密优惠券信息
     * @param goodsT {@link GoodsT}
     * @return {@link CouponTagVo} 优惠金额信息，没有相关优惠券则返回null
     */
    private CouponTagVo getGoodsCouponInfoFirst(GoodsT goodsT){
        MrkingVoucherRecord goodsCouponFirst = couponService.getGoodsCouponFirst(goodsT.getGoodsId(), goodsT.getCatId(), goodsT.getSortId(), (byte) 0);
        if (goodsCouponFirst == null) {
            return null;
        }
        CouponTagVo couponTagVo=new CouponTagVo();
        couponTagVo.setActCode(goodsCouponFirst.getActCode());
        couponTagVo.setDenomination(goodsCouponFirst.getDenomination());
        couponTagVo.setUseConsumeRestrict(goodsCouponFirst.getUseConsumeRestrict());
        if (couponTagVo.getUseConsumeRestrict() != 1) {
            couponTagVo.setLeastConsume(null);
        } else {
            couponTagVo.setLeastConsume(goodsCouponFirst.getLeastConsume());
        }
        return couponTagVo;
    }

    public boolean isIn135610Activity(Byte goodsType){
       return goodsType == GoodsConstant.ACTIVITY_TYPE_GROUP_BUY ||
              goodsType == GoodsConstant.ACTIVITY_TYPE_BARGAIN ||
              goodsType == GoodsConstant.ACTIVITY_TYPE_SEC_KILL ||
              goodsType == GoodsConstant.ACTIVITY_TYPE_REDUCE_PRICE ||
             goodsType == GoodsConstant.ACTIVITY_TYPE_PRE_SALE;
    }
}
