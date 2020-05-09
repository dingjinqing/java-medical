package com.vpu.mp.service.shop.order.virtual;

import com.vpu.mp.db.shop.tables.records.VirtualOrderRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.virtual.MemberCardOrderParam;
import com.vpu.mp.service.pojo.shop.order.virtual.MemberCardOrderVo;
import com.vpu.mp.service.pojo.shop.order.virtual.VirtualOrderRefundParam;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Arrays;

import static com.vpu.mp.db.shop.tables.MemberCard.MEMBER_CARD;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserCard.USER_CARD;
import static com.vpu.mp.db.shop.tables.VirtualOrder.VIRTUAL_ORDER;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 虚拟商品订单 - 会员卡
 *
 * @author 郑保乐
 */
@Service
public class MemberCardOrderService extends VirtualOrderService {

    public static final String MEMBER_CARD_ORDER_SN_PREFIX = "M";

    /**
     * 订单列表
     */
    public PageResult<MemberCardOrderVo> getMemberCardOrderList(MemberCardOrderParam param) {
        SelectOnConditionStep<? extends Record> select =
            db().select(VIRTUAL_ORDER.ORDER_ID, VIRTUAL_ORDER.ORDER_SN,
                VIRTUAL_ORDER.VIRTUAL_GOODS_ID, VIRTUAL_ORDER.RETURN_FLAG, VIRTUAL_ORDER.PAY_TIME, VIRTUAL_ORDER.MONEY_PAID,
                VIRTUAL_ORDER.USE_ACCOUNT, VIRTUAL_ORDER.USE_SCORE, VIRTUAL_ORDER.RETURN_TIME,VIRTUAL_ORDER.CURRENCY,VIRTUAL_ORDER.ORDER_AMOUNT,USER.USERNAME, USER.MOBILE,
                MEMBER_CARD.CARD_NAME, MEMBER_CARD.CARD_TYPE, MEMBER_CARD.PAY_FEE, MEMBER_CARD.PAY_TYPE,
                USER_CARD.CARD_NO)
                .from(VIRTUAL_ORDER)
                .leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(VIRTUAL_ORDER.VIRTUAL_GOODS_ID))
                .leftJoin(USER).on(VIRTUAL_ORDER.USER_ID.eq(USER.USER_ID))
                .leftJoin(USER_CARD).on(VIRTUAL_ORDER.VIRTUAL_GOODS_ID.eq(USER_CARD.CARD_ID));
        buildOptions(select, param);
        return getPageResult(select, param, MemberCardOrderVo.class);
    }

    /**
     * 条件查询
     */
    private void buildOptions(SelectOnConditionStep<? extends Record> select,
                              MemberCardOrderParam param) {
        String orderSn = param.getOrderSn();
        String cardNo = param.getCardNo();
        Byte cardType = param.getCardType();
        String userInfo = param.getUserInfo();
        Timestamp startTime = param.getStartTime();
        Timestamp endTime = param.getEndTime();
        Boolean isRefund = param.getRefund();
        select.where(VIRTUAL_ORDER.GOODS_TYPE.eq(GOODS_TYPE_MEMBER_CARD));
        if (isNotEmpty(orderSn)) {
            select.and(VIRTUAL_ORDER.ORDER_SN.like(format("%s%%", orderSn)));
        }
        if (isNotEmpty(userInfo)) {
            select.and(USER.USERNAME.like(format("%s%%", userInfo)).or(USER.MOBILE.like(format("%s%%", userInfo))));
        }
        if (isNotEmpty(cardNo)) {
            select.and(USER_CARD.CARD_NO.like(format("%s%%", cardNo)));
        }
        if (null != cardType) {
            select.and(MEMBER_CARD.CARD_TYPE.eq(cardType));
        }
        if (null != startTime) {
            select.and(VIRTUAL_ORDER.PAY_TIME.ge(startTime));
        }
        if (null != endTime) {
            select.and(VIRTUAL_ORDER.PAY_TIME.le(endTime));
        }
        if (null != isRefund) {
            if (isRefund) {
                select.and(VIRTUAL_ORDER.RETURN_FLAG.eq(REFUND_STATUS_SUCCESS).or(VIRTUAL_ORDER.RETURN_FLAG.eq(REFUND_STATUS_FAILED)));
            }
        }
        select.and(VIRTUAL_ORDER.DEL_FLAG.eq((byte) 0));
        select.orderBy(VIRTUAL_ORDER.ORDER_ID.desc());
    }

    /**
     * 手动退款
     */
    public JsonResultCode memberCardOrderRefund(VirtualOrderRefundParam param) {
        try {
			this.virtualOrderRefund(param);
		} catch (MpException e) {
			return e.getErrorCode();
		}

        /** 操作记录 */
        saas().getShopApp(getShopId()).record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_MEMBER_CARD_ORDER_REFUND.code }), new String[] {param.getOrderSn()});
        return null;
    }
    /**
     *更新prepayId
     * @param orderSn 订单sn
     * @param prepayId prepayId
     */
    public void updatePrepayId(String orderSn,String prepayId){
        db().update(VIRTUAL_ORDER).set(VIRTUAL_ORDER.PREPAY_ID,prepayId).where(VIRTUAL_ORDER.ORDER_SN.eq(orderSn)).execute();
    }

    public VirtualOrderRecord getRecord(String orderSn){
        return db().fetchAny(VIRTUAL_ORDER,VIRTUAL_ORDER.ORDER_SN.eq(orderSn));
    }
}
