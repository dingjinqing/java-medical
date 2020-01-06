package com.vpu.mp.service.shop.market.groupdraw;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.order.OrderListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.order.OrderListVo;
import org.jooq.Record12;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;

import static com.vpu.mp.db.shop.tables.JoinDrawList.JOIN_DRAW_LIST;
import static com.vpu.mp.db.shop.tables.JoinGroupList.JOIN_GROUP_LIST;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.OrderMust.ORDER_MUST;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author 郑保乐
 */
@Service
public class GroupDrawOrderService extends ShopBaseService {

    public PageResult<OrderListVo> getGroupDrawOrderList(OrderListParam param) {
        SelectConditionStep<Record12<String, String, String, Integer, Integer, Timestamp, String, String, Byte, Integer,
            String, Boolean>>
            select = db().select(JOIN_GROUP_LIST.ORDER_SN, ORDER_GOODS.GOODS_NAME, ORDER_GOODS.GOODS_IMG,
            JOIN_GROUP_LIST.USER_ID, ORDER_GOODS.ORDER_ID,
            ORDER_INFO.CREATE_TIME, ORDER_INFO.MOBILE, ORDER_MUST.CONSIGNEE_REAL_NAME, JOIN_GROUP_LIST.IS_WIN_DRAW,
            DSL.count(JOIN_DRAW_LIST.USER_ID).as("codeCount"), ORDER_INFO.ORDER_STATUS_NAME,
            DSL.iif(JOIN_GROUP_LIST.STATUS.eq((byte) 1), true, false).as("grouped"))
            .from(JOIN_GROUP_LIST)
            .leftJoin(ORDER_INFO).on(JOIN_GROUP_LIST.ORDER_SN.eq(ORDER_INFO.ORDER_SN))
            .leftJoin(ORDER_MUST).on(JOIN_GROUP_LIST.ORDER_SN.eq(ORDER_MUST.ORDER_SN))
            .leftJoin(JOIN_DRAW_LIST).on(JOIN_GROUP_LIST.USER_ID.eq(JOIN_DRAW_LIST.USER_ID)
                .and(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(JOIN_DRAW_LIST.GROUP_DRAW_ID)))
            .leftJoin(ORDER_GOODS).on(JOIN_GROUP_LIST.ORDER_SN.eq(ORDER_GOODS.ORDER_SN))
            .where();
        buildOptions(select, param);
        select.orderBy(JOIN_GROUP_LIST.CREATE_TIME.desc());
        return getPageResult(select, param, OrderListVo.class);
    }

    private void buildOptions(SelectConditionStep<Record12<String, String, String, Integer, Integer, Timestamp, String,
        String, Byte, Integer, String, Boolean>> select, OrderListParam param) {
        Integer groupDrawId = param.getGroupDrawId();
        String goodsName = param.getGoodsName();
        String consigneeName = param.getConsigneeName();
        String mobile = param.getMobile();
        Timestamp createTime = param.getCreateTime();
        String orderStatusName = param.getOrderStatusName();
        String orderSn = param.getOrderSn();
        select.and(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId));
        if (isNotEmpty(mobile)) {
            select.and(ORDER_INFO.MOBILE.like(this.likeValue(mobile)));
        }
        if (isNotEmpty(orderSn)) {
            select.and(JOIN_GROUP_LIST.ORDER_SN.like(this.likeValue(orderSn)));
        }
        if (isNotEmpty(goodsName)) {
            select.and(ORDER_GOODS.GOODS_NAME.like(this.likeValue(goodsName)));
        }
        if (isNotEmpty(consigneeName)) {
            select.and(ORDER_MUST.CONSIGNEE_REAL_NAME.like(this.likeValue(consigneeName)));
        }
        if (isNotEmpty(orderStatusName)) {
            select.and(ORDER_INFO.ORDER_STATUS_NAME.eq(orderStatusName));
        }
        if (null != createTime) {
            select.and(DSL.date(ORDER_INFO.CREATE_TIME).eq(new Date(createTime.getTime())));
        }
        select.groupBy(JOIN_GROUP_LIST.USER_ID, JOIN_GROUP_LIST.ORDER_SN, JOIN_GROUP_LIST.GOODS_ID, ORDER_GOODS.GOODS_IMG,
            ORDER_GOODS.GOODS_NAME, ORDER_INFO.CREATE_TIME, ORDER_INFO.MOBILE, ORDER_MUST.CONSIGNEE_REAL_NAME,
            JOIN_GROUP_LIST.CREATE_TIME,
            JOIN_GROUP_LIST.IS_WIN_DRAW, ORDER_INFO.ORDER_STATUS_NAME, ORDER_GOODS.ORDER_ID, JOIN_GROUP_LIST.STATUS);
    }
}
