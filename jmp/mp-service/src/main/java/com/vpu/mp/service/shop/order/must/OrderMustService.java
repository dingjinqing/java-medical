package com.vpu.mp.service.shop.order.must;

import com.vpu.mp.db.shop.tables.OrderMust;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.must.OrderMustVo;
import com.vpu.mp.service.pojo.wxapp.order.must.OrderMustParam;
import org.jooq.Record;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.tables.OrderMust.ORDER_MUST;

/**
 * @author: 王兵兵
 * @create: 2019-10-17 17:04
 * 下单必填信息
 **/
@Service
public class OrderMustService extends ShopBaseService {

    public final OrderMust TABLE = ORDER_MUST;
    /**
     * 按订单号查询下单必填信息
     * @param orderSn
     * @return PageResult
     */
    public OrderMustVo getOrderMustByOrderSn(String orderSn){
        Record record = db().select(TABLE.asterisk()).from(TABLE).where(TABLE.ORDER_SN.eq(orderSn)).fetchOne();
        if(record != null){
            return record.into(OrderMustVo.class);
        }else{
            return null;
        }
    }

    /**
     * 记录入库
     * @param must 订单必填信息
     */
    public void addRecord(OrderMustParam must){
        if(must == null) {
            return;
        }
        db().newRecord(TABLE, must).store();
    }
}
