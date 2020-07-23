package com.vpu.mp.service.saas.order;

import com.google.common.collect.Lists;
import com.vpu.mp.db.main.Tables;
import com.vpu.mp.db.main.tables.OrderInfo;
import com.vpu.mp.db.main.tables.OrderInfoNew;
import com.vpu.mp.db.main.tables.records.OrderInfoNewRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.db.main.Tables.ORDER_INFO;
import static com.vpu.mp.db.main.Tables.ORDER_INFO_NEW;

/**
 * @author luguangyao
 */
@Service
@Slf4j
public class OrderService extends MainBaseService {


    /**
     * 查询main库中对应门店未关闭的订单的id
     * @param shopId 店铺id
     * @return 未关闭的订单的id集合
     */
    public List<String> getNotClosedOrderIds(Integer shopId){
        return db().select(ORDER_INFO_NEW.ORDER_SN)
                    .from(ORDER_INFO_NEW)
                    .where(
                        ORDER_INFO_NEW.ORDER_STATUS
                            .notIn(
                                OrderConstant.ORDER_CANCELLED,
                                OrderConstant.ORDER_CLOSED,
                                OrderConstant.FINISHED,
                                OrderConstant.ORDER_RETURN_FINISHED,
                                OrderConstant.ORDER_REFUND_FINISHED)
                        .and(ORDER_INFO_NEW.SHOP_ID.eq(shopId)))
                    .fetch(x->x.into(String.class));
    }

    /**
     * 更新旧的需要更新的订单数据
     * @param records 需要更新的订单数据
     */
    public void updateOldData(List<OrderInfoRecord> records){
        List<OrderInfoNewRecord> updates = Lists.newArrayList();
        for( OrderInfoRecord record:records ){
            OrderInfoNewRecord newRecord = db().newRecord(ORDER_INFO_NEW);
            BeanUtils.copyProperties(record,newRecord);
            newRecord.setOrderId(null);
            updates.add(newRecord);
        }
        db().batchUpdate(updates).execute();
    }


}
