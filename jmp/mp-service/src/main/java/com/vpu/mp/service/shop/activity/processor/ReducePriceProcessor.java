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
import com.vpu.mp.service.shop.user.cart.CartService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
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
@Slf4j
public class ReducePriceProcessor implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor,ActivityCartListStrategy{
    @Autowired
    ReducePriceProcessorDao reducePriceProcessorDao;
    @Autowired
    private CartService cartService;

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

    /**
     * 限时减价
     * 限时减价-会员价-商品原价 最小的价格
     * @param cartBo 业务数据类
     */
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        log.info("购物车-限时降价-开始");
        // 是限时降价商品且不是会员专享
        List<Integer> productList = cartBo.getCartGoodsList().stream()
                .filter(goods -> BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE.equals(goods.getGoodsRecord().getGoodsType())&&goods.getBuyStatus().equals(BaseConstant.YES))
                .map(WxAppCartGoods::getProductId).collect(Collectors.toList());
        Map<Integer, List<Record5<Integer, Integer, Byte, Integer, BigDecimal>>> goodsReduceListInfo = reducePriceProcessorDao.getGoodsProductReduceList(productList, DateUtil.getLocalDateTime());
        if (goodsReduceListInfo!=null&&goodsReduceListInfo.size()>0){
            cartBo.getCartGoodsList().stream().filter(goods ->
                    goodsReduceListInfo.get(goods.getProductId()) != null
                    &&goods.getBuyStatus().equals(BaseConstant.YES)&&goods.getPriceStatus().equals(BaseConstant.NO)).forEach(goods -> {
                Record5<Integer, Integer, Byte, Integer, BigDecimal> reducePriceRecord = goodsReduceListInfo.get(goods.getProductId()).get(0);
                BigDecimal reducePrize = reducePriceRecord.get(REDUCE_PRICE_PRODUCT.PRD_PRICE);
                //价格小于商品价格限时降价才会生效
                if (reducePrize.compareTo(goods.getPrdPrice())<0){
                    Integer limitNum = reducePriceRecord.get(REDUCE_PRICE.LIMIT_AMOUNT);
                    Byte limitFlag = reducePriceRecord.get(REDUCE_PRICE.LIMIT_FLAG);
                    if (limitNum.equals(0)||goods.getCartNumber()<=limitNum||(goods.getCartNumber()>limitNum&&limitFlag.equals(BaseConstant.FIRST_SPECIAL_LIMIT_FLAG_CONFINE))){
                        log.info("购物车-限时降价-商品{}",goods.getGoodsName());
                        CartActivityInfo activityInfo = new CartActivityInfo();
                        activityInfo.setActivityType(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE);
                        activityInfo.setActivityId(reducePriceRecord.get(REDUCE_PRICE.ID));
                        activityInfo.setProductPrice(reducePrize);
                        activityInfo.setLimitMaxNum(reducePriceRecord.get(REDUCE_PRICE.LIMIT_AMOUNT));
                        activityInfo.setLimitNumberType(reducePriceRecord.get(REDUCE_PRICE.LIMIT_FLAG));
                        goods.getCartActivityInfos().add(activityInfo);
                        log.info("购物车限时减价-修改价格");
                        goods.setPriceActivityType(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE);
                        goods.setPrdPrice(reducePrize);
                        goods.setLimitMaxNum(limitNum);
                        goods.setActivityLimitType(limitFlag);
                        if (goods.getCartNumber()>limitNum&&limitFlag.equals(BaseConstant.FIRST_SPECIAL_LIMIT_FLAG_CONFINE)) {
                            log.info("购物车-限时降价-商品{}-限制商品数量{}-取消选中",goods.getGoodsName(),limitNum);
                            cartService.switchCheckedProduct(cartBo.getUserId(),goods.getCartId(),CartConstant.CART_NO_CHECKED);
                            goods.setIsChecked(CartConstant.CART_NO_CHECKED);
                            goods.setBuyStatus(BaseConstant.NO);
                        }
                    }
                }
            });
        }
        log.info("购物车-限时降价-结束");
    }
}
