package com.vpu.mp.dao.shop.order;

import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;

/**
 * @author yangpengcheng
 * @date 2020/7/28
 **/
@Repository
public class OrderInfoDao extends ShopBaseDao {
    /**
     *
     * @param orderId
     * @param auditStatus
     */
    public void updateAuditStatus(Integer orderId,Byte auditStatus){
        db().update(ORDER_INFO).set(ORDER_INFO.ORDER_AUDIT_STATUS,auditStatus).where(ORDER_INFO.ORDER_ID.eq(orderId)).execute();
    }

    /**
     * 订单状态改为待发货
     * @param orderId 订单id
     * @param prescriptionCode 处方号
     */
    public void updateAuditedToWaitDelivery(Integer orderId, String prescriptionCode) {
        db().update(ORDER_INFO)
                .set(ORDER_INFO.ORDER_AUDIT_STATUS, OrderConstant.MEDICAL_AUDIT_PASS)
                .set(ORDER_INFO.ORDER_STATUS,OrderConstant.ORDER_WAIT_DELIVERY)
                .where(ORDER_INFO.ORDER_ID.eq(orderId))
                .execute();
    }
}
