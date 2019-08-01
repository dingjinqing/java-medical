package com.vpu.mp.service.shop.order;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.virtual.MemberCardParam;
import com.vpu.mp.service.pojo.shop.order.virtual.MemberCardVo;
import org.jooq.Record13;
import org.jooq.SelectOnConditionStep;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static com.vpu.mp.db.shop.tables.CardOrder.CARD_ORDER;
import static com.vpu.mp.db.shop.tables.MemberCard.MEMBER_CARD;
import static com.vpu.mp.db.shop.tables.User.USER;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 虚拟商品订单 - 会员卡
 *
 * @author 郑保乐
 */
@Service
public class MemberCardOrderService extends ShopBaseService {

    public PageResult<MemberCardVo> getMemberCardOrderList(MemberCardParam param) {
        SelectOnConditionStep<Record13<Integer, String, Integer, Byte, Timestamp, BigDecimal, String, String, String,
            String, Byte, BigDecimal, Byte>> select = shopDb().select(CARD_ORDER.ORDER_ID, CARD_ORDER.ORDER_SN,
            CARD_ORDER.CARD_ID, CARD_ORDER.RETURN_FLAG, CARD_ORDER.PAY_TIME, CARD_ORDER.MONEY_PAID, CARD_ORDER.CARD_NO,
            USER.USERNAME, USER.MOBILE, MEMBER_CARD.CARD_NAME, MEMBER_CARD.CARD_TYPE, MEMBER_CARD.PAY_FEE, MEMBER_CARD.PAY_TYPE)
            .from(CARD_ORDER)
            .leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(CARD_ORDER.CARD_ID))
            .leftJoin(USER).on(CARD_ORDER.USER_ID.eq(USER.USER_ID));
        buildOptions(select, param);
        return getPageResult(select, param, MemberCardVo.class);
    }

    private void buildOptions(SelectOnConditionStep<Record13<Integer, String, Integer, Byte, Timestamp, BigDecimal,
        String, String, String, String, Byte, BigDecimal, Byte>> select, MemberCardParam param) {
        String orderSn = param.getOrderSn();
        String cardNo = param.getCardNo();
        Byte cardType = param.getCardType();
        String userInfo = param.getUserInfo();
        Timestamp startTime = param.getStartTime();
        Timestamp endTime = param.getEndTime();
        Boolean isRefund = param.getRefund();
        if (isNotEmpty(orderSn)) {
            select.where(CARD_ORDER.ORDER_SN.like(format("%s%%", orderSn)));
        }
        if (isNotEmpty(userInfo)) {
            select.where(USER.USERNAME.like(format("%s%%", userInfo)))
                .or(USER.MOBILE.like(format("%s%%", userInfo)));
        }
        if (isNotEmpty(cardNo)) {
            select.where(CARD_ORDER.CARD_NO.like(format("%s%%", cardNo)));
        }
        if (null != cardType) {
            select.where(MEMBER_CARD.CARD_TYPE.eq(cardType));
        }
        if (null != startTime) {
            select.where(CARD_ORDER.PAY_TIME.ge(startTime));
        }
        if (null != endTime) {
            select.where(CARD_ORDER.PAY_TIME.le(endTime));
        }
        if (null != isRefund && isRefund) {
            select.where(CARD_ORDER.RETURN_FLAG.eq(MemberCardParam.PART_REFUND))
                .or(CARD_ORDER.RETURN_FLAG.eq(MemberCardParam.TOTAL_REFUND));
        }
    }
}
