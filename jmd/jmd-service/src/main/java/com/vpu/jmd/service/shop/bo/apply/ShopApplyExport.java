package com.vpu.jmd.service.shop.bo.apply;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 升级/续费申请导出
 * @author liangchen
 * @date 2020.06.04
 */
@Data
@ExcelSheet
public class ShopApplyExport {
    /** 店铺id */
    @ExcelColumn(columnIndex = 0, columnName = JsonResultMessage.CHARGE_RENEW_SHOP_ID)
    private Integer shopId;
    /** 店铺名称 */
    @ExcelColumn(columnIndex = 1, columnName = JsonResultMessage.CHARGE_RENEW_SHOP_NAME)
    private String shopName;
    /** 手机号 */
    @ExcelColumn(columnIndex = 2, columnName = JsonResultMessage.CHARGE_RENEW_MOBILE)
    private String mobile;
    /** 店铺创建时间 */
    @ExcelColumn(columnIndex = 3, columnName = JsonResultMessage.CHARGE_RENEW_CREATED_TIME)
    private Timestamp shopCreated;
    /** 店铺状态 0已过期 1使用中 */
    @ExcelColumn(columnIndex = 4, columnName = JsonResultMessage.CHARGE_RENEW_SHOP_STATUS)
    private String shopStatus;
    /** 店铺类型 */
    @ExcelColumn(columnIndex = 5, columnName = JsonResultMessage.CHARGE_RENEW_SHOP_TYPE)
    private String shopType;
    /** 所属账号 */
    @ExcelColumn(columnIndex = 6, columnName = JsonResultMessage.CHARGE_RENEW_PARENT_ACCOUNT)
    private String userName;
    /** 申请升级或续费账号 */
    @ExcelColumn(columnIndex = 7, columnName = JsonResultMessage.CHARGE_RENEW_ACCOUNT)
    private String applyName;
    /** 申请账号类型 0主账号 1子账号*/
    @ExcelColumn(columnIndex = 8, columnName = JsonResultMessage.CHARGE_RENEW_ACCOUNT_TYPE)
    private String accountType;
    /** 申请时间 */
    @ExcelColumn(columnIndex = 9, columnName = JsonResultMessage.CHARGE_RENEW_APPLY_TIME)
    private Timestamp created;
    /** 申请类型 0升级1续费 */
    @ExcelColumn(columnIndex = 10, columnName = JsonResultMessage.CHARGE_RENEW_APPLY_TYPE)
    private String applyType;
    /** 点击模块 */
    @ExcelColumn(columnIndex = 11, columnName = JsonResultMessage.CHARGE_RENEW_MODULE)
    private String applyMod;
    /** 是否联系 0未联系1已联系 */
    @ExcelColumn(columnIndex = 12, columnName = JsonResultMessage.CHARGE_RENEW_CONTACT)
    private String contact;
}
