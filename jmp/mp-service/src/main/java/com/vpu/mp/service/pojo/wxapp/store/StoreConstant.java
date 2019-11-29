package com.vpu.mp.service.pojo.wxapp.store;

import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * @author liufei
 * @date 11/28/19
 */
public class StoreConstant {
    /**
     * 门店买单订单状态，0待付款， 1已付款
     */
    public static final Byte WAIT_TO_PAY = BYTE_ZERO;
    public static final Byte PAY_SUCCESS = BYTE_ONE;
    public static final String WAIT_TO_PAY_NAME = "待付款";
    public static final String PAY_SUCCESS_NAME = "已付款";
}