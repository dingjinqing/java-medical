package com.vpu.mp.service.pojo.shop.market.presale;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 定金膨胀活动订单出参
 *
 * @author 郑保乐
 */
@Data
@ExcelSheet
public class OrderExcelVo {

    /** 订单id **/
    @ExcelIgnore
    private Integer orderId;
    /** 手机号 **/
    @ExcelIgnore
    private String mobile;
    /** 收货人姓名 **/
    @ExcelIgnore
    private String consigneeRealName;
    /** 快递费金额 **/
    @ExcelIgnore
    private BigDecimal shippingFee;
    /** 下单时间 **/
    @ExcelIgnore
    private Timestamp createTime;
    /** 支付金额 **/
    @ExcelIgnore
    private BigDecimal moneyPaid;

    /** 订单流水号 **/
    @ExcelColumn(columnName = JsonResultMessage.PRESALE_ORDER_SN, columnIndex = 0)
    private String orderSn;
    /** 商品名 **/
    @ExcelColumn(columnName = JsonResultMessage.PRESALE_GOODS_NAME, columnIndex = 1)
    private String goodsName;
    /** 下单时间 **/
    @ExcelColumn(columnName = JsonResultMessage.PRESALE_ORDER_TIME, columnIndex = 2)
    private String orderTime;
    /** 订单状态名 **/
    @ExcelColumn(columnName = JsonResultMessage.PRESALE_ORDER_STATUS, columnIndex = 3)
    private String orderStatusName;
    /** 商品数量 **/
    @ExcelColumn(columnName = JsonResultMessage.PRESALE_GOODS_AMOUNT, columnIndex = 4)
    private Integer goodsAmount;
    /** 收货人信息 **/
    @ExcelColumn(columnName = JsonResultMessage.PRESALE_CONSIGNEE_INFO, columnIndex = 5)
    private String consigneeInfo;
    /** 支付金额 **/
    @ExcelColumn(columnName = JsonResultMessage.PRESALE_MONEY_PAID, columnIndex = 6)
    private String money;
}
