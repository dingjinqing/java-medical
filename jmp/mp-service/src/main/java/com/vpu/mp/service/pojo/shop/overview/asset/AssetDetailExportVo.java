package com.vpu.mp.service.pojo.shop.overview.asset;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumnNotNull;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author liufei
 * @date 2019/8/5
 */
@Data
@ExcelSheet
public class AssetDetailExportVo {
    @ExcelColumn(columnIndex = 0,columnName = "overview.asset.management.trade_time")
    @ExcelColumnNotNull
    private Timestamp tradeTime;
    @ExcelColumn(columnIndex = 1, columnName = "overview.asset.management.trade_sn")
    @ExcelColumnNotNull
    private String tradeSn;
    @ExcelColumn(columnIndex = 2,columnName = "overview.asset.management.trade_num")
    @ExcelColumnNotNull
    private BigDecimal tradeNum;
    @ExcelColumn(columnIndex = 3, columnName = "overview.asset.management.username")
    @ExcelColumnNotNull
    private String username;
    @ExcelIgnore
    private Integer userId;
    @ExcelColumn(columnIndex = 4,columnName = "overview.asset.management.trade_type")
    @ExcelColumnNotNull
    private Byte tradeType;
    @ExcelColumn(columnIndex = 5,columnName = "overview.asset.management.trade_flow")
    @ExcelColumnNotNull
    private Byte tradeFlow;
    @ExcelColumn(columnIndex = 6,columnName = "overview.asset.management.trade_status")
    @ExcelColumnNotNull
    private Byte tradeStatus;
    @ExcelColumn(columnIndex = 7, columnName = "overview.asset.management.real_name")
    @ExcelColumnNotNull
    private String realName;
    @ExcelColumn(columnIndex = 8, columnName = "overview.asset.management.mobile")
    @ExcelColumnNotNull
    private String mobile;
}
