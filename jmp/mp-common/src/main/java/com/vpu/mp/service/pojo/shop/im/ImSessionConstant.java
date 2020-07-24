package com.vpu.mp.service.pojo.shop.im;

/**
 * @author 赵晓东
 * @description 定义问诊表常量
 * @create 2020-07-24 13:58
 **/

public class ImSessionConstant {

    /**
     * 待问诊
     */
    public static final Byte IM_SESSION_STATUS_NOT_USE = 0;
    /**
     * 问诊中
     */
    public static final Byte IM_SESSION_STATUS_USING = 1;

    /**
     * 会话取消
     */
    public static final Byte IM_SESSION_STATUS_CANCEL_USE = 2;
    /**
     * 会话结束
     */
    public static final Byte IM_SESSION_STATUS_ALREADY_USE = 3;


}
