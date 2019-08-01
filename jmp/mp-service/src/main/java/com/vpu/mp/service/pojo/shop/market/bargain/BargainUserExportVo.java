package com.vpu.mp.service.pojo.shop.market.bargain;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @description: 帮忙砍价用户列表导出pojo
 * @author: 王兵兵
 * @create: 2019-08-01 11:26
 **/
@ExcelSheet
@Data
public class BargainUserExportVo {

    /**
     * 主键
     */
    @ExcelColumn(columnName = "bargain.user.list.id",columnIndex = 0)
    private Integer id;

    @ExcelColumn(columnName = "bargain.user.list.username",columnIndex = 1)
    private String username;

    @ExcelColumn(columnName = "bargain.user.list.mobile",columnIndex = 2)
    private String mobile;

    @ExcelColumn(columnName = "bargain.user.list.create_time",columnIndex = 3)
    private Timestamp createTime;

    /**
     * 帮砍金额
     */
    @ExcelColumn(columnName = "bargain.user.list.bargain_money",columnIndex = 4)
    private BigDecimal bargainMoney;
}
