package com.vpu.mp.service.pojo.shop.auth;


/**
 * @author chenjie
 * @date 2020年08月20日
 */
public class StoreAuthConstant {
    /**
     * 店员类型
     */
    public static final Byte STORE_CLERK=1;
    /**
     * 店长类型
     */
    public static final Byte STORE_DIRECTOR=2;
    /**
     * 未删除
     */
    public static final Byte DEL_NORMAL=0;
    /**
     * StoreAuthFlag
     */
    public static final Boolean STORE_AUTH_OK=true;

    /**
     * 门店后台登录msg
     */

    /**
     * 门店账号不存在
     */
    public static final String ACCOUNT_NOT_EXIST="accountNotExist";
    /**
     * 门店账号已删除
     */
    public static final String ACCOUNT_IS_DELETE="accountIsDelete";
    /**
     * 门店账号已禁用
     */
    public static final String ACCOUNT_IS_FORBIDDEN="accountIsForbidden";
    /**
     * 门店账号下门店为空
     */
    public static final String STORE_IS_EMPTY="storeIsEmpty";
    /**
     * 门店开关未打开
     */
    public static final String STORE_SWITCH_CLOSE="storeSwitchClose";
    /**
     * 门店不在该门店账号下（店员登录）
     */
    public static final String STORE_NOT_EXIST="storeNotExist";


}
