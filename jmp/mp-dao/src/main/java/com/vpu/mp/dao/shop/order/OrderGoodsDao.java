package com.vpu.mp.dao.shop.order;

import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;

/**
 * @author 孔德成
 * @date 2020/7/13 14:41
 */
@Repository
public class OrderGoodsDao extends ShopBaseDao {

    /**
     * 根据订单号获取订单商品的规格id
     * @param orderSn 订单号
     * @return 规格ids
     */
    public List<Integer> getProductIdByOrderSn(String orderSn) {
        return db().select(ORDER_GOODS.PRODUCT_ID).from(ORDER_GOODS).where(ORDER_GOODS.ORDER_SN.eq(orderSn)).fetch(ORDER_GOODS.PRODUCT_ID);
    }

    /**
     *
     */
    public void  listGoodsOldPrescription(){
        db().select(ORDER_GOODS.ORDER_ID,ORDER_GOODS.PRESCRIPTION_OLD_CODE)
                .from(ORDER_GOODS)
                .where(ORDER_GOODS.MEDICAL_AUDIT_TYPE.eq(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT))
                .groupBy(ORDER_GOODS.ORDER_ID,ORDER_GOODS.PRESCRIPTION_OLD_CODE)
                .fetch();
    }
}
