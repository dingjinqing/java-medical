package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.db.shop.tables.records.SecKillDefineRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.SecKillPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.SeckillMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.SecKillDefine.SEC_KILL_DEFINE;
import static com.vpu.mp.db.shop.tables.SecKillList.SEC_KILL_LIST;
import static com.vpu.mp.db.shop.tables.SecKillProductDefine.SEC_KILL_PRODUCT_DEFINE;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class SecKillProcessorDao extends ShopBaseService {

    @Autowired
    SeckillService seckillService;

    /**
     * 获取商品集合内的秒杀信息
     * @param goodsIds 商品id集合
     * @param date 日期
     * @return key:商品id，value:List<Record3<Integer, Integer, BigDecimal>> SEC_KILL_PRODUCT_DEFINE.SK_ID, SEC_KILL_PRODUCT_DEFINE.GOODS_ID, SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE
     */
    public  Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> getGoodsSecKillListInfo(List<Integer> goodsIds, Timestamp date){
        return db().select(SEC_KILL_PRODUCT_DEFINE.SK_ID, SEC_KILL_PRODUCT_DEFINE.GOODS_ID, SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE)
            .from(SEC_KILL_DEFINE).innerJoin(SEC_KILL_PRODUCT_DEFINE).on(SEC_KILL_DEFINE.SK_ID.eq(SEC_KILL_PRODUCT_DEFINE.SK_ID))
            .where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(SEC_KILL_DEFINE.END_TIME.gt(date))
            .and(SEC_KILL_DEFINE.START_TIME.le(date))
            .and(SEC_KILL_PRODUCT_DEFINE.GOODS_ID.in(goodsIds))
            .orderBy(SEC_KILL_DEFINE.FIRST.desc(),SEC_KILL_DEFINE.CREATE_TIME.desc(),SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(SEC_KILL_PRODUCT_DEFINE.GOODS_ID)));
    }

    /**
     * 组装输出详情页的秒杀信息
     * @param skId
     * @param userId
     * @param capsule
     * @return
     */
    public SeckillMpVo getDetailSeckillInfo(Integer skId,Integer userId,GoodsDetailMpBo capsule){
        SeckillMpVo seckillVo = new SeckillMpVo();

        SecKillDefineRecord secKill =db().selectFrom(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.SK_ID.eq(skId).and(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())))
            .fetchAny();

        seckillVo.setActivityId(skId);
        seckillVo.setActivityType(BaseConstant.ACTIVITY_TYPE_SEC_KILL);

        seckillVo.setActState(this.canApplySecKill(secKill,capsule.getGoodsNumber(),userId,capsule.getGoodsId()));
        if (BaseConstant.ACTIVITY_STATUS_NOT_HAS.equals(seckillVo.getActState())) {
            return  seckillVo;
        }

        seckillVo.setStock(secKill.getStock());
        seckillVo.setLimitAmount(secKill.getLimitAmount());
        seckillVo.setLimitPaytime(secKill.getLimitPaytime());
        if (BaseConstant.ACTIVITY_STATUS_NOT_START.equals(seckillVo.getActState())) {
            seckillVo.setStartTime((secKill.getStartTime().getTime()- DateUtil.getLocalDateTime().getTime())/1000);
        }
        seckillVo.setEndTime((secKill.getEndTime().getTime()-DateUtil.getLocalDateTime().getTime())/1000);

        seckillVo.setCardId(secKill.getCardId());
        seckillVo.setShareConfig(Util.parseJson(secKill.getShareConfig(), ShopShareConfig.class));
        seckillVo.setActProducts(this.getSecKillPrd(secKill.getSkId(),capsule));

        seckillVo.setSaleNum(secKill.getSaleNum() + secKill.getBaseSale());
        return seckillVo;
    }


    /**
     * 判断秒杀活动的可用状态
     * @param secKill 秒杀基本信息
     * @param goodsNumber goods表的库存
     * @return 0正常;1该活动不存在;2该活动已停用;3该活动未开始;4该活动已结束;5商品已抢光;6该用户已达到限购数量上限;7该秒杀为会员专属，该用户没有对应会员卡
     */
    private Byte canApplySecKill(SecKillDefineRecord secKill,Integer goodsNumber,Integer userId,Integer goodsId) {
        return seckillService.canApplySecKill(secKill,goodsNumber,userId,goodsId);
    }

    /**
     * 获取秒杀活动信息
     * @param secProductList 规格ids
     * @param date 时间
     * @return
     */
    public Result<? extends Record> getSecKillInfoList(List<Integer> secProductList, Timestamp date) {
         return db().select(SEC_KILL_DEFINE.GOODS_ID,SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID,SEC_KILL_DEFINE.SK_ID,SEC_KILL_DEFINE.CARD_ID
         ,SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE)
                .from(SEC_KILL_DEFINE)
                .leftJoin(SEC_KILL_PRODUCT_DEFINE).on(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(SEC_KILL_DEFINE.SK_ID))
                .where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
                .and(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.in(secProductList))
                .and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
                .and(SEC_KILL_DEFINE.START_TIME.lt(date))
                .and(SEC_KILL_DEFINE.END_TIME.gt(date))
                .fetch();
    }

    /**
     * 获取秒杀活动信息
     * @param productId 规格id
     * @param date 时间
     * @return
     */
    public Result<? extends Record> getSecKillInfoList(Integer productId, Timestamp date) {
         return db().select(SEC_KILL_DEFINE.GOODS_ID,SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID,SEC_KILL_DEFINE.SK_ID,SEC_KILL_DEFINE.CARD_ID
         ,SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE)
                .from(SEC_KILL_DEFINE)
                .leftJoin(SEC_KILL_PRODUCT_DEFINE).on(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(SEC_KILL_DEFINE.SK_ID))
                .where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
                .and(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(productId))
                .and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
                .and(SEC_KILL_DEFINE.START_TIME.lt(date))
                .and(SEC_KILL_DEFINE.END_TIME.gt(date))
                .fetch();
    }

    /**
     * 取秒杀下的规格
     * @param skId
     * @return
     */
    private List<SecKillPrdMpVo> getSecKillPrd(Integer skId,GoodsDetailMpBo capsule){
        List<SecKillPrdMpVo> list = db().select(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID,SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE,SEC_KILL_PRODUCT_DEFINE.STOCK,SEC_KILL_PRODUCT_DEFINE.TOTAL_STOCK).from(SEC_KILL_PRODUCT_DEFINE).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(skId)).fetchInto(SecKillPrdMpVo.class);

        //填入原价，方便计算
        Map<Integer,BigDecimal> prdPriceMap = capsule.getProducts().stream().collect(Collectors.toMap(GoodsPrdMpVo::getPrdId,GoodsPrdMpVo::getPrdRealPrice));
        list.forEach(vo->{
            vo.setPrdPrice(prdPriceMap.get(vo.getProductId()));
        });
        return list;
    }

    /**
     * 订单确认页--处理秒杀下单前的价格 或 下单时再次计算订单价格
     * @param orderBeforeParam
     */
    public void setOrderPrdSeckillPrice(OrderBeforeParam orderBeforeParam){
        SecKillDefineRecord seckill = db().selectFrom(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.SK_ID.eq(orderBeforeParam.getActivityId())).fetchAny();
        //是否免运费
        orderBeforeParam.setIsFreeShippingAct(seckill.getFreeFreight());

        //秒杀规格价
        for(OrderBeforeParam.Goods prd : orderBeforeParam.getGoods()){
            Record2<BigDecimal, BigDecimal> record = db().select(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE,GOODS_SPEC_PRODUCT.PRD_PRICE).from(SEC_KILL_PRODUCT_DEFINE).leftJoin(GOODS_SPEC_PRODUCT).on(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID)).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(orderBeforeParam.getActivityId()).and(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(prd.getProductId()))).fetchSingle();

            //秒杀价
            prd.setProductPrice(record.value1());

            if(seckill.getLimitAmount() != null && seckill.getLimitAmount() > 0){
                //跳过普通商品的限购校验
                prd.setIsAlreadylimitNum(true);
            }
        }

    }

    /**
     * 秒杀下单-库存处理
     * @param order
     */
    public void processSeckillStock(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        SecKillDefineRecord seckill = getSeckillActById(order.getActivityId());
        for(OrderGoodsBo goods : param.getBos()){
            if(goods.getIsGift().equals(OrderConstant.IS_GIFT_N)) {
                if (seckill.getStock() - goods.getGoodsNumber() < 0) {
                    //秒杀库存不足
                    throw new MpException(JsonResultCode.CODE_ORDER_GOODS_LOW_STOCK);
                }

                int seckillPrdStock = db().select(SEC_KILL_PRODUCT_DEFINE.STOCK).from(SEC_KILL_PRODUCT_DEFINE).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(order.getActivityId()).and(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(goods.getProductId()))).fetchSingle().into(Integer.class);
                if (seckillPrdStock - goods.getGoodsNumber() < 0) {
                    //秒杀规格库存不足
                    throw new MpException(JsonResultCode.CODE_ORDER_GOODS_LOW_STOCK);
                }

                //修改库存
                db().update(SEC_KILL_DEFINE).set(SEC_KILL_DEFINE.STOCK, seckill.getStock() - goods.getGoodsNumber()).set(SEC_KILL_DEFINE.SALE_NUM, SEC_KILL_DEFINE.SALE_NUM.add(goods.getGoodsNumber())).where(SEC_KILL_DEFINE.SK_ID.eq(order.getActivityId())).execute();
                db().update(SEC_KILL_PRODUCT_DEFINE).set(SEC_KILL_PRODUCT_DEFINE.STOCK, seckillPrdStock - goods.getGoodsNumber()).set(SEC_KILL_PRODUCT_DEFINE.SALE_NUM, SEC_KILL_PRODUCT_DEFINE.SALE_NUM.add(goods.getGoodsNumber())).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(order.getActivityId()).and(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(goods.getProductId()))).execute();

                //秒杀记录
                seckillService.seckillList.addSecRecord(order, param.getGoods().get(0).getGoodsId());
            }
        }
        //设置订单过期时间
        order.setExpireTime(DateUtil.getTimeStampPlus(seckill.getLimitPaytime(), ChronoUnit.MINUTES));

    }

    public void processReturn(ReturnOrderRecord returnOrderRecord,Integer activityId, List<OrderReturnGoodsVo> returnGoods){
        String orderSn = null;
        for (OrderReturnGoodsVo g : returnGoods){
            //不是赠品行，返还活动库存
            if(g.getIsGift().equals(OrderConstant.IS_GIFT_N)){
                orderSn = g.getOrderSn();
                seckillService.updateSeckillStock(activityId,g.getProductId(),- g.getGoodsNumber());
            }
        }
        if(returnOrderRecord == null && orderSn != null){
            //取消或关闭订单时
            //删除秒杀记录
            db().update(SEC_KILL_LIST).set(SEC_KILL_LIST.DEL_FLAG,DelFlag.DISABLE_VALUE).where(SEC_KILL_LIST.ORDER_SN.eq(orderSn)).execute();
        }
    }

    /**
     * 取单个活动
     * @param actId
     * @return
     */
    private SecKillDefineRecord getSeckillActById(int actId){
        return db().fetchAny(SEC_KILL_DEFINE,SEC_KILL_DEFINE.SK_ID.eq(actId));
    }

}
