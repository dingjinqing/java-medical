package com.vpu.jmd.service.shop.bo.apply;


/**
 * 升级/续费申请常量
 * @author liangchen
 * @date 2020.06.03
 */
public class ShopApplyConstant {
    /** 店铺状态使用中 */
    public static final Byte SHOP_IS_USING = 1;
    /** 店铺状态已过期 */
    public static final Byte SHOP_IS_EXPIRED = 0;
    /** 申请账号类型主账号 */
    public static final Byte ACCOUNT_MAIN = 0;
    /** 申请账号类型子账号 */
    public static final Byte ACCOUNT_SUB = 1;
    /** 申请类型：全部 */
    public static final Byte APPLY_TYPE_ALL = -1;
    /** 申请类型：升级 */
    public static final Byte APPLY_TYPE_CHARGE = 0;
    /** 申请类型：续费 */
    public static final Byte APPLY_TYPE_RENEW = 1;
    /** 是否联系：全部 */
    public static final Byte HAS_CONTACT_AND_NOT = -1;
    /** 是否联系：未联系 */
    public static final Byte HAS_NOT_CONTACT = 0;
    /** 是否联系：已联系 */
    public static final Byte HAS_CONTACT = 1;
    /** 店铺状态使用中 */
    public static final String SHOP_IS_USING_STRING = "使用中";
    /** 店铺状态已过期 */
    public static final String SHOP_IS_EXPIRED_STRING = "已过期";
    /** v1 */
    public static final String V1 = "体验版";
    /** v2 */
    public static final String V2 = "基础版";
    /** v3 */
    public static final String V3 = "高级版";
    /** v4 */
    public static final String V4 = "旗舰版";
    /** 子账号 */
    public static final String SUB_ACCOUNT = "子账号";
    /** 主账号 */
    public static final String MAIN_ACCOUNT = "主账号";
    /** 升级 */
    public static final String CHARGE = "升级";
    /** 续费 */
    public static final String RENEW = "续费";
    /** 未联系 */
    public static final String HAS_NOT_CONTACT_STRING = "未联系";
    /** 已联系 */
    public static final String HAS_CONTACT_STRING = "已联系";
    /** 表格导出 */
    public static final String LANGUAGE_TYPE_EXCEL = "excel";
}
