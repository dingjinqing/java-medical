package com.vpu.mp.service.pojo.shop.order.shipping;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2019-10-17 13:52
 **/
@Getter
@Setter
public class BaseShippingInfoVo {
    protected String orderSn;
    /**物流单号*/
    protected String shippingNo;
    /**快递公司id*/
    protected Byte shippingId;
    /**物流名称*/
    protected String shippingName;
    /**发货时间*/
    protected Timestamp shippingTime;
    /**确认收货时间*/
    protected Timestamp confirmTime;
}
