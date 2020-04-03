package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleVo;
import com.vpu.mp.service.pojo.shop.market.presale.PresaleConstant;
import com.vpu.mp.service.pojo.shop.market.presale.ProductVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.presale.PreSaleMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.presale.PreSalePrdMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.presale.OrderPreSale;
import com.vpu.mp.service.shop.activity.dao.PreSaleProcessorDao;
import com.vpu.mp.service.shop.order.action.base.Calculate;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.cart.CartService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record3;
import org.jooq.Record5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Presale.PRESALE;
import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;

/**
 * 预售
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
@Slf4j
public class PreSaleProcessor implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor,CreateOrderProcessor,ActivityCartListStrategy {

    @Autowired
    PreSaleProcessorDao preSaleProcessorDao;

    @Autowired
    OrderInfoService order;
    @Autowired
    private CartService cartService;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_PRE_SALE_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_PRE_SALE;
    }

    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        List<GoodsListMpBo> availableCapsules = capsules.stream().filter(x -> BaseConstant.ACTIVITY_TYPE_PRE_SALE.equals(x.getActivityType())).collect(Collectors.toList());
        List<Integer> goodsIds = availableCapsules.stream().map(GoodsListMpBo::getGoodsId).collect(Collectors.toList());
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsPreSaleListInfo = preSaleProcessorDao.getGoodsPreSaleListInfo(goodsIds, DateUtil.getLocalDateTime());

        availableCapsules.forEach(capsule->{
            if (goodsPreSaleListInfo.get(capsule.getGoodsId()) == null) {
                return;
            }
            Record3<Integer, Integer, BigDecimal> record3 = goodsPreSaleListInfo.get(capsule.getGoodsId()).get(0);
            capsule.setRealPrice(record3.get(PRESALE_PRODUCT.PRESALE_PRICE));
            GoodsActivityBaseMp activity = new GoodsActivityBaseMp();
            activity.setActivityId(record3.get(PRESALE.ID));
            activity.setActivityType(BaseConstant.ACTIVITY_TYPE_PRE_SALE);
            capsule.getGoodsActivities().add(activity);
            capsule.getProcessedTypes().add(BaseConstant.ACTIVITY_TYPE_PRE_SALE);

        });
    }

    /*****************商品详情处理*******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        if (param.getActivityId() == null || !BaseConstant.ACTIVITY_TYPE_PRE_SALE.equals(param.getActivityType())) {
            return;
        }

        PreSaleMpVo goodsPreSaleInfo = preSaleProcessorDao.getGoodsPreSaleInfo(param.getActivityId(),param.getGoodsId(), DateUtil.getLocalDateTime());

        if (BaseConstant.ACTIVITY_STATUS_NOT_HAS.equals(goodsPreSaleInfo.getActState())) {
            capsule.setActivity(goodsPreSaleInfo);
            return;
        }

        Map<Integer, GoodsPrdMpVo> prdMap = capsule.getProducts().stream().collect(Collectors.toMap(GoodsPrdMpVo::getPrdId, Function.identity()));
        List<PreSalePrdMpVo> preSalePrdMpVos = goodsPreSaleInfo.getPreSalePrdMpVos();

        int goodsNum = 0;
        int saleNumber = 0;
        List<PreSalePrdMpVo> newPreSalePrds = new ArrayList<>(prdMap.size());
        for (PreSalePrdMpVo preSalePrd : preSalePrdMpVos) {
            GoodsPrdMpVo goodsPrdMpVo = prdMap.get(preSalePrd.getProductId());
            if (goodsPrdMpVo == null) {
                prdMap.remove(preSalePrd.getProductId());
                continue;
            }
            // 库存数量从新设置
            if (preSalePrd.getStock() > goodsPrdMpVo.getPrdNumber()) {
                preSalePrd.setStock(goodsPrdMpVo.getPrdNumber());
            }
            goodsNum+=preSalePrd.getStock();
            saleNumber+=preSalePrd.getSaleNumber();
            preSalePrd.setPrdPrice(goodsPrdMpVo.getPrdRealPrice());
            newPreSalePrds.add(preSalePrd);
        }

        capsule.setProducts(new ArrayList<>(prdMap.values()));
        capsule.setGoodsNumber(goodsNum);
        if (goodsNum == 0 && BaseConstant.needToConsiderNotHasNum(goodsPreSaleInfo.getActState())) {
            log.debug("小程序-商品详情-预售商品数量已用完");
            goodsPreSaleInfo.setActState(BaseConstant.ACTIVITY_STATUS_NOT_HAS_NUM);
        }
        if (prdMap.size() == 0) {
            log.debug("小程序-商品详情-预售活动-商品规格信息和活动规格信息无交集");
            goodsPreSaleInfo.setActState(BaseConstant.ACTIVITY_STATUS_NO_PRD_TO_USE);
        }
        capsule.setGoodsSaleNum(saleNumber);
        goodsPreSaleInfo.setPreSalePrdMpVos(newPreSalePrds);
        capsule.setActivity(goodsPreSaleInfo);
    }

    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        PreSaleVo activityInfo = preSaleProcessorDao.getDetail(param.getActivityId());
        preSaleProcessorDao.orderCheck(param, activityInfo);
        preSaleProcessorDao.orderInit(param, activityInfo);
    }



    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        //无需处理
    }

    @Override
    public void processOrderEffective(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    @Override
    public void processUpdateStock(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        log.info("预售下单更新库存start");
        Map<Integer, Integer> updateParam = param.getBos().stream()
            .filter(x -> OrderConstant.IS_GIFT_N.equals(x.getIsGift()))
            .collect(Collectors.toMap(OrderGoodsBo::getProductId, OrderGoodsBo::getGoodsNumber));
        if(updateParam.size() != 0){
            preSaleProcessorDao.updateStockAndSales(updateParam, order.getActivityId());
        }
        log.info("预售下单更新库存end,data{}",updateParam);
    }

    @Override
    public void processReturn(ReturnOrderRecord returnOrderRecord, Integer activityId, List<OrderReturnGoodsVo> returnGoods) throws MpException {
        Map<Integer, Integer> updateParam = returnGoods.stream().collect(Collectors.toMap(OrderReturnGoodsVo::getProductId, OrderReturnGoodsVo::getGoodsNumber));
        updateParam.forEach((k, v)->updateParam.put(k, -v));
        if(updateParam.size() != 0){
            preSaleProcessorDao.updateStockAndSales(updateParam, activityId);
        }
    }

    /**
     * 订单处理金额
     * @param param
     * @param bos
     * @param tolalNumberAndPrice
     * @param vo
     * @return
     */
    public OrderPreSale calculate(OrderBeforeParam param, List<OrderGoodsBo> bos, BigDecimal[] tolalNumberAndPrice, OrderBeforeVo vo) {
        log.info("预售处理金额start");
        PreSaleVo activityInfo = preSaleProcessorDao.getDetail(param.getActivityId());
        BigDecimal totalPreSaleMoney = null;
        BigDecimal discount = null;
        for (OrderGoodsBo orderGoodsBo : bos) {
            for (ProductVo productVo : activityInfo.getProducts()) {
                if(productVo.getProductId().equals(orderGoodsBo.getProductId())) {
                    totalPreSaleMoney = BigDecimalUtil.add(totalPreSaleMoney , BigDecimalUtil.multiply( new BigDecimal(productVo.getPresaleMoney().toString()), new BigDecimal(orderGoodsBo.getGoodsNumber())));
                    if (PresaleConstant.PRE_SALE_ONE_PHASE.equals(activityInfo.getPrePayStep()) || param.getDate().before(activityInfo.getPreEndTime())) {
                        //定金一期 || 定金二期第一期
                        discount = BigDecimalUtil.subtrac(
                            new BigDecimal(productVo.getPreDiscountMoney1().toString()), new BigDecimal(productVo.getPresaleMoney().toString()))
                            .multiply(new BigDecimal((orderGoodsBo.getGoodsNumber())));
                    } else {
                        //定金二期
                        discount = BigDecimalUtil.subtrac(
                            new BigDecimal(productVo.getPreDiscountMoney2().toString()), new BigDecimal(productVo.getPresaleMoney().toString()))
                            .multiply(new BigDecimal((orderGoodsBo.getGoodsNumber())));
                    }
                }
            }
        }
        OrderPreSale result = new OrderPreSale();
        result.setTotalPreSaleMoney(totalPreSaleMoney);
        result.setInfo(activityInfo);
        result.setTotalDiscount(discount);
        result.setTotalPrice(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE]);
        result.setTotalGoodsNumber(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_NUMBER]);
        result.setIdentity(activityInfo.getId().toString());
        result.setBos(bos);
        result.initRatio();
        vo.setBkShippingTime(PresaleConstant.DELIVER_TYPE_TIME.equals(activityInfo.getDeliverType()) ? activityInfo.getDeliverTime() : Timestamp.from(Instant.now().plusSeconds(Duration.ofDays(activityInfo.getDeliverDays()).getSeconds())));
        vo.setBkReturnType(activityInfo.getReturnType());
        log.info("预售处理金额end");
        return result;
    }

    //*******************购物车***************/
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        log.info("购物车-预售-开始");
        //预购商品
        List<Integer> productList = cartBo.getCartGoodsList().stream()
                .filter(goods -> BaseConstant.ACTIVITY_TYPE_PRE_SALE.equals(goods.getGoodsRecord().getGoodsType()))
                .map(WxAppCartGoods::getProductId).collect(Collectors.toList());
        Map<Integer, List<Record5<Integer, Integer,Integer, Integer, BigDecimal>>> goodsPreSaleList = preSaleProcessorDao.getGoodsPreSaleList(productList, cartBo.getDate());
        if (goodsPreSaleList!=null&&goodsPreSaleList.size()>0){
            cartBo.getCartGoodsList().forEach(goods->{
                if (goodsPreSaleList.get(goods.getProductId())!=null){
                    log.info("购物车-预售商品-不可选中,不可购买");
                    Record5<Integer, Integer,Integer, Integer, BigDecimal> record5s = goodsPreSaleList.get(goods.getProductId()).get(0);
                    CartActivityInfo seckillProductInfo =new CartActivityInfo();
                    seckillProductInfo.setActivityType(BaseConstant.ACTIVITY_TYPE_PRE_SALE);
                    seckillProductInfo.setActivityId(record5s.get(PRESALE.ID));
                    seckillProductInfo.setProductPrice(record5s.get(PRESALE_PRODUCT.PRESALE_PRICE));
                    goods.getCartActivityInfos().add(seckillProductInfo);
                    goods.setActivityType(BaseConstant.ACTIVITY_TYPE_PRE_SALE);
                    goods.setActivityId(record5s.get(PRESALE.ID));
                    goods.setIsChecked(CartConstant.CART_NO_CHECKED);
                    goods.setBuyStatus(BaseConstant.NO);
                    if (goods.getIsChecked().equals(CartConstant.CART_IS_CHECKED)){
                        cartService.switchCheckedProduct(cartBo.getUserId(),goods.getCartId(),CartConstant.CART_NO_CHECKED);
                    }
                }
            });
        }
        log.info("购物车-预售-结束");
    }
}
