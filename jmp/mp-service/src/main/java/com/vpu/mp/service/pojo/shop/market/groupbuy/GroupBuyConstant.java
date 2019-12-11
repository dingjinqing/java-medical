package com.vpu.mp.service.pojo.shop.market.groupbuy;

/**
 * @author 孔德成
 * @date 2019/12/10 16:18
 */
public class GroupBuyConstant {

    //*********** 是否是团长  **************
    /**
     * 是
     */
    public static final Byte IS_GROUPER_Y = 1;
    /**
     * 否
     */
    public static final Byte IS_GROUPER_N = 0;

    //********** 开团限制 0不限制**************

    public static final Byte OPEN_LIMIT_N = 0;

    //*********** 参团限制 0不限制 **************

    public static final Byte JOIN_LIMIT_N = 0;


    //*********** 拼团状态 ******************
    /**
     * 拼团中
     */
    public static final Byte STATUS_ONGOING = 0;
    /**
     * 拼团成功
     */
    public static final Byte STATUS_SUCCESS = 1;
    /**
     * 拼团失败
     */
    public static final Byte STATUS_FAILED = 2;
}
