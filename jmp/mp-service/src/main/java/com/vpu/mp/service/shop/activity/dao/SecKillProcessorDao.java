package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.SecKillDefineRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import org.jooq.Record3;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.SecKillDefine.SEC_KILL_DEFINE;
import static com.vpu.mp.db.shop.tables.SecKillList.SEC_KILL_LIST;
import static com.vpu.mp.db.shop.tables.SecKillProductDefine.SEC_KILL_PRODUCT_DEFINE;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class SecKillProcessorDao extends ShopBaseService {

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
            .and(SEC_KILL_DEFINE.STATUS.eq(GoodsConstant.USE_STATUS))
            .and(SEC_KILL_DEFINE.END_TIME.gt(date))
            .and(SEC_KILL_DEFINE.GOODS_ID.in(goodsIds))
            .orderBy(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(SEC_KILL_DEFINE.GOODS_ID)));
    }

    /**
     * 判断秒杀活动的可用状态
     * @param skId
     * @param goodsNumber goods表的库存
     * @return 1该活动不存在;2该活动已停用;3该活动未开始;4该活动已结束;5商品已抢光;0正常
     */
    public Integer canApplySecKill(Integer skId,Integer goodsNumber) {
        SecKillDefineRecord secKill = (SecKillDefineRecord) db().select(SEC_KILL_DEFINE.asterisk()).from(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.SK_ID.eq(skId)).fetchOne();
        if(secKill == null){
            return 1;
        }
        if(secKill.getStatus() == BaseConstant.ACTIVITY_STATUS_DISABLE){
            return 2;
        }
        if(secKill.getStartTime().after(DateUtil.getLocalDateTime())){
            return 3;
        }
        if(secKill.getEndTime().before(DateUtil.getLocalDateTime())){
            return 4;
        }
        int minStock = goodsNumber < secKill.getStock() ? goodsNumber : secKill.getStock();
        if(minStock <= 0){
            return 5;
        }
        return 0;
    }

    /**
     *
     * @param skId
     * @param userId
     * @return 已对该活动秒杀下单的数量
     */
    public Integer getUserSeckilledGoodsNumber(Integer skId,Integer userId) {
        return db().select(DSL.sum(ORDER_INFO.GOODS_AMOUNT)).from(SEC_KILL_LIST).leftJoin(ORDER_INFO).on(SEC_KILL_LIST.ORDER_SN.eq(ORDER_INFO.ORDER_SN)).where(SEC_KILL_LIST.SK_ID.eq(skId).and(SEC_KILL_LIST.USER_ID.eq(userId)).and(SEC_KILL_LIST.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))).groupBy(ORDER_INFO.USER_ID).fetchOne().into(Integer.class);
    }
}
