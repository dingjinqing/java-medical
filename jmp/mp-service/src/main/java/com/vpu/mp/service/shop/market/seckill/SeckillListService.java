package com.vpu.mp.service.shop.market.seckill;

import static com.vpu.mp.db.shop.tables.SecKillList.SEC_KILL_LIST;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillDetailPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillDetailPageListQueryVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author: 王兵兵
 * @create: 2019-08-07 14:31
 **/
@Service
public class SeckillListService extends ShopBaseService {
    public PageResult<SeckillDetailPageListQueryVo> getSeckillDetailPageList(SeckillDetailPageListQueryParam param){
        SelectWhereStep<? extends  Record> select =
        db().select(GOODS.GOODS_NAME,SEC_KILL_LIST.USER_ID,USER.USERNAME,USER.MOBILE,SEC_KILL_LIST.ORDER_SN,SEC_KILL_LIST.CREATE_TIME,ORDER_INFO.GOODS_AMOUNT).
        from(SEC_KILL_LIST).innerJoin(USER).on(SEC_KILL_LIST.USER_ID.eq(USER.USER_ID)).innerJoin(ORDER_INFO).on(SEC_KILL_LIST.ORDER_SN.eq(ORDER_INFO.ORDER_SN)).innerJoin(GOODS).on(SEC_KILL_LIST.GOODS_ID.eq(GOODS.GOODS_ID));
        select = buildOptions(select,param);

        return getPageResult(select,param.getCurrentPage(),param.getPageRows(),SeckillDetailPageListQueryVo.class);
    }

    public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select,SeckillDetailPageListQueryParam param){
        select.where(SEC_KILL_LIST.SK_ID.eq(param.getSkId()));
        select.where(SEC_KILL_LIST.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        select.where(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_CLOSED));
        if(!StringUtils.isEmpty(param.getMobile())){
            select.where(USER.MOBILE.contains(param.getMobile()));
        }
        if(!StringUtils.isEmpty(param.getUsername())){
            select.where(USER.USERNAME.contains(param.getUsername()));
        }
        if(param.getMinGoodsAmount() != null && param.getMinGoodsAmount() > 0){
            select.where(ORDER_INFO.GOODS_AMOUNT.ge(param.getMinGoodsAmount()));
        }

        if(param.getMaxGoodsAmount() != null && param.getMaxGoodsAmount() > 0){
            select.where(ORDER_INFO.GOODS_AMOUNT.le(param.getMaxGoodsAmount()));
        }
        return select;
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

    /**
     *
     * @param skId
     * @param productId
     * @return 某活动待付款订单占用的库存
     */
    public int getUnpaidSeckillNumberByPrd(int skId,int productId){
        return db().select(DSL.sum(ORDER_INFO.GOODS_AMOUNT)).from(SEC_KILL_LIST).leftJoin(ORDER_INFO).on(SEC_KILL_LIST.ORDER_SN.eq(ORDER_INFO.ORDER_SN)).leftJoin(ORDER_GOODS).on(ORDER_GOODS.ORDER_SN.eq(ORDER_INFO.ORDER_SN)).where(SEC_KILL_LIST.SK_ID.eq(skId).and(SEC_KILL_LIST.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).and(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_PAY)).and(ORDER_GOODS.PRODUCT_ID.eq(productId))).fetchOptional().ofNullable(0).get();
    }
}
