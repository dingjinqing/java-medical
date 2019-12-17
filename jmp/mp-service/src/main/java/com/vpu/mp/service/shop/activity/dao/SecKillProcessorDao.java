package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.SecKillDefineRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.SecKillPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.SeckillMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.SecKillDefine.SEC_KILL_DEFINE;
import static com.vpu.mp.db.shop.tables.SecKillProductDefine.SEC_KILL_PRODUCT_DEFINE;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;

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
     * @return key:商品id，value:List<Record3<Integer, Integer, BigDecimal>> SEC_KILL_DEFINE.SK_ID, SEC_KILL_DEFINE.GOODS_ID, SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE
     */
    public  Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> getGoodsSecKillListInfo(List<Integer> goodsIds, Timestamp date){
        return db().select(SEC_KILL_DEFINE.SK_ID, SEC_KILL_DEFINE.GOODS_ID, SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE)
            .from(SEC_KILL_DEFINE).innerJoin(SEC_KILL_PRODUCT_DEFINE).on(SEC_KILL_DEFINE.SK_ID.eq(SEC_KILL_PRODUCT_DEFINE.SK_ID))
            .where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(SEC_KILL_DEFINE.END_TIME.gt(date))
            .and(SEC_KILL_DEFINE.GOODS_ID.in(goodsIds))
            .orderBy(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(SEC_KILL_DEFINE.GOODS_ID)));
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

        SecKillDefineRecord secKill = db().select(SEC_KILL_DEFINE.asterisk()).from(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.SK_ID.eq(skId)).fetchOne().into(SecKillDefineRecord.class);

        seckillVo.setActivityId(skId);
        seckillVo.setActivityType(BaseConstant.ACTIVITY_TYPE_SEC_KILL);

        seckillVo.setActState(this.canApplySecKill(secKill,capsule.getGoodsNumber(),userId));
        seckillVo.setStock(secKill.getStock());
        seckillVo.setLimitAmount(secKill.getLimitAmount());
        seckillVo.setLimitPaytime(secKill.getLimitPaytime());
        seckillVo.setStartTime(secKill.getStartTime());
        seckillVo.setEndTime(secKill.getEndTime());
        seckillVo.setCardId(secKill.getCardId());
        seckillVo.setShareConfig(Util.parseJson(secKill.getShareConfig(), ShopShareConfig.class));
        seckillVo.setActProducts(this.getSecKillPrd(secKill.getSkId(),capsule));
        return seckillVo;
    }


    /**
     * 判断秒杀活动的可用状态
     * @param secKill 秒杀基本信息
     * @param goodsNumber goods表的库存
     * @return 0正常;1该活动不存在;2该活动已停用;3该活动未开始;4该活动已结束;5商品已抢光;6该用户已达到限购数量上限;7该秒杀为会员专属，该用户没有对应会员卡
     */
    private Byte canApplySecKill(SecKillDefineRecord secKill,Integer goodsNumber,Integer userId) {
        return seckillService.canApplySecKill(secKill,goodsNumber,userId);
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
        for(OrderBeforeParam.Goods prd : orderBeforeParam.getGoods()){
            Record2<BigDecimal, BigDecimal> record = db().select(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE,GOODS_SPEC_PRODUCT.PRD_PRICE).from(SEC_KILL_PRODUCT_DEFINE).leftJoin(GOODS_SPEC_PRODUCT).on(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID)).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(orderBeforeParam.getActivityId()).and(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(prd.getProductId()))).fetchSingle();

            //秒杀价
            prd.setProductPrice(record.value1());
        }

    }

    /**
     * 秒杀下单-库存处理
     * @param order
     */
    public void processSeckillStock(OrderBeforeVo order) throws MpException {
        for(OrderGoodsBo goods : order.getOrderGoods()){
            int seckillStock = db().select(SEC_KILL_DEFINE.STOCK).from(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.SK_ID.eq(order.getActivityId())).fetchSingle().into(Integer.class);
            if(seckillStock - goods.getGoodsNumber() < 0){
                //秒杀库存不足
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_LOW_STOCK);
            }

            int seckillPrdStock = db().select(SEC_KILL_PRODUCT_DEFINE.STOCK).from(SEC_KILL_PRODUCT_DEFINE).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(order.getActivityId()).and(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(goods.getProductId()))).fetchSingle().into(Integer.class);
            if(seckillPrdStock - goods.getGoodsNumber() < 0){
                //秒杀规格库存不足
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_LOW_STOCK);
            }

            db().update(SEC_KILL_DEFINE).set(SEC_KILL_DEFINE.STOCK,seckillStock - goods.getGoodsNumber()).where(SEC_KILL_DEFINE.SK_ID.eq(order.getActivityId())).execute();
            db().update(SEC_KILL_PRODUCT_DEFINE).set(SEC_KILL_PRODUCT_DEFINE.STOCK,seckillPrdStock - goods.getGoodsNumber()).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(order.getActivityId()).and(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(goods.getProductId()))).execute();
        }

    }

}
