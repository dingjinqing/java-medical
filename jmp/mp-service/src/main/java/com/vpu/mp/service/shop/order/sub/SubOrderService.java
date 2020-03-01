package com.vpu.mp.service.shop.order.sub;

import com.vpu.mp.db.shop.tables.SubOrderInfo;
import com.vpu.mp.db.shop.tables.UserDetail;
import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.db.shop.tables.records.SubOrderInfoRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.write.operate.pay.instead.InsteadPayDetailsVo;
import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import org.jooq.SelectLimitStep;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 代付订单的支付订单
 * @author 王帅
 */
@Service
public class SubOrderService  extends ShopBaseService {

    private SubOrderInfo TABLE = SubOrderInfo.SUB_ORDER_INFO;
    private UserDetail TABLE_USER_DETAIL = UserDetail.USER_DETAIL;

    public SubOrderInfoRecord create(String orderSn, BigDecimal money, String message, Integer userId, String UserName){
        logger().info("代付生成sub order start");
        SubOrderInfoRecord order = db().newRecord(TABLE);
        //生成orderSn
        order.setSubOrderSn(IncrSequenceUtil.generateOrderSn(OrderConstant.INSTEAD_PAY_SN_PREFIX));
        order.setMainOrderSn(orderSn);
        order.setUserId(userId);
        order.setUsername(UserName);
        order.setOrderStatus(OrderConstant.SubOrderConstant.SUB_ORDER_WAIT_PAY);
        order.setMoneyPaid(money);
        order.setPayCode(OrderConstant.PAY_CODE_WX_PAY);
        order.setMessage(message);
        order.store();
        order.refresh();
        logger().info("代付生成sub order end");
        return order;
    }

    public void updatePrepayId(SubOrderInfoRecord subOrder, WebPayVo webPayVo) {
        subOrder.setPrepayId(webPayVo.getResult().getPrepayId());
    }

    public PageResult<InsteadPayDetailsVo> paymentDetails(String orderSn, Integer currentPage, Integer pageRows){
        SelectLimitStep<?> select = db().select(TABLE_USER_DETAIL.USER_AVATAR, TABLE.SUB_ORDER_SN, TABLE.MAIN_ORDER_SN, TABLE.USER_ID, TABLE.USERNAME, TABLE.ORDER_STATUS, TABLE.MONEY_PAID, TABLE.REFUND_MONEY, TABLE.MESSAGE)
            .from(TABLE).leftJoin(TABLE_USER_DETAIL).on(TABLE.USER_ID.eq(TABLE_USER_DETAIL.USER_ID))
            .where(TABLE.MAIN_ORDER_SN.eq(orderSn).and(TABLE.ORDER_STATUS.in(OrderConstant.SubOrderConstant.SUB_ORDER_PAY_OK, OrderConstant.SubOrderConstant.SUB_ORDER_REFUND_SUCESS)))
            .orderBy(TABLE.PAY_TIME.desc());
        return getPageResult(select, currentPage, pageRows, InsteadPayDetailsVo.class);
    }

    public SubOrderInfoRecord get(String subOrderSn){
        return db().selectFrom(TABLE).where(TABLE.SUB_ORDER_SN.eq(subOrderSn)).fetchAny();
    }

    public void finish(String subOrderSn, PaymentRecordRecord record){
        SubOrderInfoRecord order = get(subOrderSn);
        order.setOrderStatus(OrderConstant.SubOrderConstant.SUB_ORDER_PAY_OK);
        order.setPaySn(record.getPaySn());
        order.setPayTime(DateUtil.getSqlTimestamp());
        order.update();
    }
}
