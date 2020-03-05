package com.vpu.mp.service.shop.task.market;

import com.vpu.mp.db.shop.tables.records.VirtualOrderRecord;
import com.vpu.mp.service.shop.market.couponpack.CouponPackService;
import com.vpu.mp.service.shop.order.virtual.CouponPackOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 王兵兵
 * @create: 2020-03-04 18:36
 **/
@Service
public class CouponPackTaskService {
    @Autowired
    private CouponPackOrderService couponPackOrderService;
    @Autowired
    private CouponPackService couponPackService;

    /**
     *
     */
    public void monitorCouponPackOrders(){
        List<VirtualOrderRecord> orderRecordList = couponPackOrderService.getCanGrantCouponOrderList();
        couponPackService.sendCouponPack(orderRecordList);
    }
}
