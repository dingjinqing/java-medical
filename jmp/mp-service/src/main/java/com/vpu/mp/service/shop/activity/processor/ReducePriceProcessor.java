package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.cart.activity.GoodsActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.activity.OrderCartProductBo;
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
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.activity.dao.ReducePriceProcessorDao;
import com.vpu.mp.service.shop.market.reduceprice.ReducePriceService;
import com.vpu.mp.service.shop.user.cart.CartService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record10;
import org.jooq.Record3;
import org.jooq.Record5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.ReducePrice.REDUCE_PRICE;
import static com.vpu.mp.db.shop.tables.ReducePriceProduct.REDUCE_PRICE_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 * 限时降价 返利
 */
@Service
@Slf4j
public class ReducePriceProcessor implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor,ActivityCartListStrategy,CreateOrderProcessor{
    @Autowired
    ReducePriceProcessorDao reducePriceProcessorDao;
    @Autowired
    private CartService cartService;
    @Autowired
    ReducePriceService reducePriceService;

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
                    if (limitNum.equals(0)||goods.getCartNumber()<=limitNum||(goods.getCartNumber()>limitNum&&limitFlag.equals(BaseConstant.LIMIT_FLAG_CONFINE))){
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
                        if (goods.getCartNumber()>limitNum&&limitFlag.equals(BaseConstant.LIMIT_FLAG_CONFINE)) {
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

    /**
     * 初始化参数,活动校验
     *
     * @param param 参数
     * @throws MpException 异常
     */
    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        doOrderOperation(param.createOrderCartProductBo());
    }

    public void doOrderOperation(OrderCartProductBo productBo) {
        log.info("限时降价计算start");
        Map<Integer, Record10<Integer, Integer, BigDecimal, String, String, Byte, Byte, Timestamp, Integer, Byte>> prdPriceMap = reducePriceService.getProductReducePrice(productBo.getProductIds());
        productBo.getAll().forEach(goods -> {
            if(prdPriceMap.get(goods.getProductId()) != null){
                log.info("规格：{},限时降价价 ：{}", goods.getProductId(), prdPriceMap.get(goods.getProductId()));
                GoodsActivityInfo goodsActivityInfo = new GoodsActivityInfo();
                goodsActivityInfo.setActivityType(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE);
                goodsActivityInfo.setReducePricePrdPrice(prdPriceMap.get(goods.getProductId()).get(REDUCE_PRICE_PRODUCT.PRD_PRICE));
                goodsActivityInfo.setActivityId(prdPriceMap.get(goods.getProductId()).get(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID));
                goodsActivityInfo.setLimitAmount(prdPriceMap.get(goods.getProductId()).get(REDUCE_PRICE.LIMIT_AMOUNT));
                goodsActivityInfo.setLimitFlag(prdPriceMap.get(goods.getProductId()).get(REDUCE_PRICE.LIMIT_FLAG));
                goods.getActivityInfo().put(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE, goodsActivityInfo);
            }
        });
        log.info("限时降价计算end");
    }

    /**
     * 保存信息,此时订单数据已计算完成（此时订单状态已经明确）
     *
     * @param param 参数
     * @param order 订单
     * @throws MpException 异常
     */
    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    /**
     * 订单生效后（微信支付、其他支付、货到付款等）的营销后续处理（库存、活动状态相关）
     * 下单时接口调用说明：此方法与扣减商品库存（非活动库存）方法同时调用
     * 支付回调调用说明：订单状态转化未待发货时调用
     *
     * @param param
     * @param order
     * @throws MpException
     */
    @Override
    public void processOrderEffective(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    /**
     * 更新活动库存
     *
     * @param param
     * @param order
     * @throws MpException
     */
    @Override
    public void processUpdateStock(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    /**
     * 退货、取消、关闭时更新（returnOrderRecord == null为关闭或取消）
     *
     * @param returnOrderRecord
     * @param activityId        活动id
     * @param returnGoods       退款商品
     */
    @Override
    public void processReturn(ReturnOrderRecord returnOrderRecord, Integer activityId, List<OrderReturnGoodsVo> returnGoods) throws MpException {

    }
}
