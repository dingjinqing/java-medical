package com.vpu.mp.service.shop.order.info;

import com.google.common.collect.ImmutableMap;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.order.OrderListMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderListParam;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;

/**
 * mp端订单业务
 *
 * @author 王帅 return
 *         不可变map,key为all,waitPay,waitDelivery,shipped,finished,returning
 *         value默认为0
 *
 */
@Service
public class MpOrderInfoService extends OrderInfoService{


    /**
     * 个人中心订单状态数量展示
     * @param userId
     * @param param
     * @param isContainSubOrder
     * @return
     */
    public Map<Byte,Integer> getOrderStatusNum(Integer userId, OrderListParam param, boolean isContainSubOrder) {
        //搜索条件
        Condition condition = DSL.noCondition();
        if(param != null && !StringUtils.isBlank(param.getSearch())) {
            condition = condition.and(TABLE.ORDER_SN.contains(param.getSearch()).or(ORDER_GOODS.GOODS_NAME.contains(param.getSearch())));
        }
        //基础状态数量
        Map<Byte, Integer> countMap = setIsContainSearch(db().select(DSL.countDistinct(TABLE.ORDER_ID), TABLE.ORDER_STATUS).from(TABLE), param).
            where(setIsContainSubOrder(TABLE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(TABLE.USER_ID.eq(userId)), isContainSubOrder).and(condition)).
            groupBy(TABLE.ORDER_STATUS).
            fetch().
            intoMap(TABLE.ORDER_STATUS , DSL.count());
        //退款退货
        Integer refund = setIsContainSearch(db().select(DSL.countDistinct(TABLE.ORDER_ID)).from(TABLE), param).
            where(setIsContainSubOrder(TABLE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(TABLE.REFUND_STATUS.gt(OrderConstant.REFUND_DEFAULT_STATUS)).and(TABLE.USER_ID.eq(userId)),isContainSubOrder).and(condition)).
            fetchOneInto(Integer.class);
        //初始化不可变map
        Map<Byte,Integer> result = ImmutableMap.<Byte,Integer>builder()
            .put(OrderConstant.ALL,
                countMap.values().stream().reduce(0, Integer::sum))
            .put(OrderConstant.WAIT_PAY,
                mapDefaultValue(countMap , OrderConstant.ORDER_WAIT_PAY))
            .put(OrderConstant.WAIT_DELIVERY,
                mapDefaultValue(countMap , OrderConstant.ORDER_WAIT_DELIVERY))
            .put(OrderConstant.SHIPPED,
                mapDefaultValue(countMap , OrderConstant.ORDER_SHIPPED))
            .put(OrderConstant.FINISHED,
                (mapDefaultValue(countMap , OrderConstant.ORDER_FINISHED)) + mapDefaultValue(countMap , OrderConstant.ORDER_RECEIVED))
            .put(OrderConstant.REFUND,
                refund == null ? Integer.valueOf(0) : refund)
            .build();
        return result;
    }

    private SelectWhereStep<?> setIsContainSearch(SelectJoinStep<?> from, OrderListParam param) {
        if(param != null && !StringUtils.isBlank(param.getSearch())) {
            from.leftJoin(ORDER_GOODS).on(TABLE.ORDER_ID.eq(ORDER_GOODS.ORDER_ID));
        }
        return from;
    }

    /**
     * Map value type is Integer,set default value 0
     * @param countMap
     * @param status
     * @return
     */
    private Integer mapDefaultValue(Map<Byte, Integer> countMap , Byte status) {
        Integer temp = countMap.get(status);
        if(temp == null) {
            return Integer.valueOf(0);
        }
        return temp;
    }

    /**
     * 是否包含子单
     * @param condition
     * @param isContainSubOrder
     * @return
     */
    private Condition setIsContainSubOrder(Condition condition , boolean isContainSubOrder) {
        return isContainSubOrder ? condition : condition.and(TABLE.MAIN_ORDER_SN.eq(StringUtils.EMPTY).or(TABLE.MAIN_ORDER_SN.eq(TABLE.ORDER_SN)));

    }

    /**
     * mp端订列表查询
     * @param param
     * @return
     */
    public PageResult<OrderListMpVo> getPageList(OrderListParam param){
        SelectJoinStep<Record> select = db().selectDistinct(TABLE.asterisk()).from(TABLE);
        buildOptions(select, param, false);
        PageResult<OrderListMpVo> pageResult = getPageResult(select,param.getCurrentPage(),param.getPageRows(),OrderListMpVo.class);
        return pageResult;

    }
    public void getOrderInfoList(List<String> orderList){
        db().select(TABLE.ORDER_ID,TABLE.ORDER_SN,ORDER_GOODS.GOODS_NAME).from(TABLE).leftJoin(ORDER_GOODS).on(TABLE.ORDER_ID.eq(ORDER_GOODS.GOODS_ID))
            .where(TABLE.ORDER_SN.in(orderList));

    }

    /**
     * 构造查询条件
     */
    public SelectWhereStep<?> buildOptions(SelectJoinStep<?> select, OrderListParam param, boolean isContainSubOrder) {
        if(param == null) {
            return select;
        }
        select.where(setIsContainSubOrder(TABLE.USER_ID.eq(param.getWxUserInfo().getUserId()).and(TABLE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())), isContainSubOrder));
        if(!StringUtils.isBlank(param.getSearch())) {
            select.leftJoin(ORDER_GOODS).on(TABLE.ORDER_ID.eq(ORDER_GOODS.ORDER_ID)).
                where(TABLE.ORDER_SN.contains(param.getSearch()).or(ORDER_GOODS.GOODS_NAME.contains(param.getSearch())));
        }
        select.orderBy(TABLE.ORDER_ID.desc());
        switch (param.getType()) {
            case OrderConstant.ALL:
                break;
            case OrderConstant.WAIT_PAY:
                select.where(TABLE.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_PAY));
                break;
            case OrderConstant.WAIT_DELIVERY:
                select.where(TABLE.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_DELIVERY));
                break;
            case OrderConstant.SHIPPED:
                select.where(TABLE.ORDER_STATUS.eq(OrderConstant.ORDER_SHIPPED));
                break;
            case OrderConstant.FINISHED:
                select.where(TABLE.ORDER_STATUS.in(OrderConstant.ORDER_RECEIVED , OrderConstant.ORDER_FINISHED).and(TABLE.REFUND_STATUS.eq(OrderConstant.REFUND_DEFAULT_STATUS)));
                break;
            case OrderConstant.REFUND:
                select.where(TABLE.REFUND_STATUS.gt(OrderConstant.REFUND_DEFAULT_STATUS));
                break;
            default:
                break;
        }
        return select;
    }
}
