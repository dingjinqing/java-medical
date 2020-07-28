package com.vpu.mp.dao.shop.order;

import com.vpu.mp.common.pojo.shop.table.OrderMedicalHistoryDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.Tables.ORDER_MEDICAL_HISTORY;

/**
 * 订单历史诊断
 * @author 孔德成
 * @date 2020/7/28 14:43
 */
@Repository
public class OrderMedicalHistoryDao extends ShopBaseDao {

    public OrderMedicalHistoryDo getByOrderId(Integer orderId){
        return db().select().from(ORDER_MEDICAL_HISTORY).where(ORDER_MEDICAL_HISTORY.ORDER_ID.eq(orderId)).fetchOneInto(OrderMedicalHistoryDo.class);
    }

}
