package com.vpu.mp.service.shop.task.store;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.shop.config.TradeService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.tables.ServiceOrder.SERVICE_ORDER;

/**
 * @author: 王兵兵
 * @create: 2020-05-08 11:05
 **/
@Service
public class ServiceOrderCancelTaskService extends ShopBaseService {

    @Autowired
    private TradeService tradeCfg;

    /**
     * 自动取消待付款的服务预约订单
     * 过期时间依照店铺交易配置的 待付款订单取消时间 cancel_time
     */
    public void cancelExpireOrder() {
        Timestamp time = DateUtil.getDalyedDateTime(-tradeCfg.getCancelTime() * 60);
        String cancelReason = "超时未支付自动取消";
        db().update(SERVICE_ORDER)
            .set(SERVICE_ORDER.ORDER_STATUS, ServiceOrderService.ORDER_STATUS_CANCELED)
            .set(SERVICE_ORDER.ORDER_STATUS_NAME, ServiceOrderService.ORDER_STATUS_NAME_CANCELED)
            .set(SERVICE_ORDER.CANCELLED_TIME, DateUtil.getLocalDateTime())
            .set(SERVICE_ORDER.CANCEL_REASON, cancelReason)
            .where(SERVICE_ORDER.ORDER_STATUS.eq(ServiceOrderService.ORDER_STATUS_WAIT_PAY))
            .and(SERVICE_ORDER.CREATE_TIME.le(time))
            .execute();
    }
}
