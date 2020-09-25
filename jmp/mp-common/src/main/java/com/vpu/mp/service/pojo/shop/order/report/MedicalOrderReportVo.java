package com.vpu.mp.service.pojo.shop.order.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.common.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.common.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.common.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2020/7/31 18:26
 */
@Data
@ExcelSheet
public class MedicalOrderReportVo {

    @ExcelIgnore
    public static final String EXPORT_FILE_NAME ="销售报表导出-";

    @JsonIgnore
    @ExcelIgnore
    private Timestamp createTime;
    /**
     * 时间
     */
    @ExcelColumn(columnName = "日期", columnIndex = 0)
    private String time;
    /**
     * 订单金额
     */
    @ExcelColumn(columnName = "销售金额", columnIndex = 1)
    private BigDecimal orderAmount;
    /**
     * 订单数量
     */
    @ExcelColumn(columnName = "销售单数", columnIndex = 2)
    private Integer orderNumber;
    /**
     * 邮费
     */
    @ExcelColumn(columnName = "邮费", columnIndex = 3)
    private BigDecimal shippingFee;
    /**
     * 订单数量
     */
    @ExcelColumn(columnName = "处方药订单数量", columnIndex = 4)
    private Integer prescriptionOrderNum;
    /**
     * 邮费
     */
    @ExcelColumn(columnName = "处方药订单销售金额", columnIndex = 5)
    private BigDecimal prescriptionOrderAmount;
    /**
     * 用户余额
     */
    @ExcelColumn(columnName = "用户余额", columnIndex = 6)
    private BigDecimal useAccount;
    /**
     * 微信支付
     */
    @ExcelColumn(columnName = "微信支付", columnIndex = 7)
    private BigDecimal moneyPaid;
    /**
     * 退款金额
     */
    @ExcelColumn(columnName = "退款金额", columnIndex = 8)
    private BigDecimal returnAmount;
    /**
     * 退款订单数
     */
    @ExcelColumn(columnName = "退款单数", columnIndex = 9)
    private Integer returnNumber;
    /**
     * 净销售额
     */
    @ExcelColumn(columnName = "净销售额", columnIndex = 10)
    private BigDecimal netSales;
    /**
     * 订单平均价
     */
    @ExcelColumn(columnName = "笔单价", columnIndex = 11)
    private BigDecimal orderAvg;
}
