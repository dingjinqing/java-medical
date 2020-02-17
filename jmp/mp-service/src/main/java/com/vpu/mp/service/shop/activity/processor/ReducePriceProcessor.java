package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.reduce.ReducePriceMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.reduce.ReducePricePrdMpVo;
import com.vpu.mp.service.shop.activity.dao.ReducePriceProcessorDao;
import org.jooq.Record3;
import org.jooq.Record4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Presale.PRESALE;
import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;
import static com.vpu.mp.db.shop.tables.ReducePrice.REDUCE_PRICE;
import static com.vpu.mp.db.shop.tables.ReducePriceGoods.REDUCE_PRICE_GOODS;
import static com.vpu.mp.db.shop.tables.ReducePriceProduct.REDUCE_PRICE_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 * 限时降价 返利
 */
@Service
public class ReducePriceProcessor implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor,ActivityCartListStrategy{
    @Autowired
    ReducePriceProcessorDao reducePriceProcessorDao;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_REDUCE_PRICE_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE;
    }

    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        // 是限时降价商品且不是会员专享
        List<GoodsListMpBo> availableCapsule = capsules.stream().filter(x -> BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE.equals(x.getActivityType()) && x.getProcessedTypes().size() == 0).collect(Collectors.toList());
        List<Integer> goodsIds = availableCapsule.stream().map(GoodsListMpBo::getGoodsId).collect(Collectors.toList());
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsReduceListInfo = reducePriceProcessorDao.getGoodsReduceListInfo(goodsIds, DateUtil.getLocalDateTime());

        availableCapsule.forEach(capsule->{
            if (goodsReduceListInfo.get(capsule.getGoodsId()) == null) {
                return;
            }
            Record3<Integer, Integer, BigDecimal> record3 = goodsReduceListInfo.get(capsule.getGoodsId()).get(0);

            capsule.setRealPrice(record3.get(REDUCE_PRICE_PRODUCT.PRD_PRICE));
            GoodsActivityBaseMp activity = new GoodsActivityBaseMp();
            activity.setActivityId(record3.get(REDUCE_PRICE.ID));
            activity.setActivityType(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE);
            capsule.getGoodsActivities().add(activity);
            capsule.getProcessedTypes().add(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE);
        });
    }

    /*****************商品详情处理*******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        // 已经被其它活动处理则退出
        if (capsule.getActivity() != null) {
            return;
        }

        ReducePriceMpVo reducePriceInfo = reducePriceProcessorDao.getReducePriceInfo(param.getGoodsId(), DateUtil.getLocalDateTime());
        if (reducePriceInfo == null) {
            return;
        }

        Map<Integer, GoodsPrdMpVo> prdMap = capsule.getProducts().stream().collect(Collectors.toMap(GoodsPrdMpVo::getPrdId, Function.identity()));

        List<ReducePricePrdMpVo> newReducePrds = reducePriceInfo.getReducePricePrdMpVos().stream().filter(reducePrd -> {
            GoodsPrdMpVo goodsPrdMpVo = prdMap.get(reducePrd.getProductId());
            if (goodsPrdMpVo == null) {
                return false;
            } else {
                reducePrd.setPrdPrice(goodsPrdMpVo.getPrdRealPrice());
                return true;
            }
        }).collect(Collectors.toList());

        reducePriceInfo.setReducePricePrdMpVos(newReducePrds);
        capsule.setActivity(reducePriceInfo);
    }

    //****************购物车***********************
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        // 是限时降价商品且不是会员专享
        List<Integer> productList = cartBo.getCartGoodsList().stream()
                .filter(goods -> BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE.equals(goods.getGoodsRecord().getGoodsType()))
                .map(WxAppCartGoods::getProductId).collect(Collectors.toList());
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsReduceListInfo = reducePriceProcessorDao.getGoodsProductReduceList(productList, DateUtil.getLocalDateTime());

        if (goodsReduceListInfo!=null&&goodsReduceListInfo.size()>0){
            cartBo.getCartGoodsList().stream().filter(goods -> goodsReduceListInfo.get(goods.getProductId()) != null).forEach(goods -> {
                Record3<Integer, Integer, BigDecimal> record3 = goodsReduceListInfo.get(goods.getProductId()).get(0);
                CartActivityInfo activityInfo = new CartActivityInfo();
                activityInfo.setActivityType(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE);
                activityInfo.setActivityId(record3.get(REDUCE_PRICE.ID));
                activityInfo.setSecKillPrice(record3.get(REDUCE_PRICE_PRODUCT.PRD_PRICE));
                goods.getCartActivityInfos().add(activityInfo);
                goods.setActivityType(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE);
                goods.setActivityId(record3.get(REDUCE_PRICE.ID));
            });
        }
    }
}
