package com.vpu.mp.dao.shop.order;

import com.vpu.mp.dao.foundation.base.ShopBaseDao;
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
        db().update(ORDER_INFO).set(ORDER_INFO.ORDER_AUDIT_STATUS,auditStatus).where(ORDER_INFO.ORDER_ID.eq(orderId));
    }
}
