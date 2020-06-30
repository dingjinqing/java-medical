package com.vpu.mp.service.pojo.shop.store.verifier;

import com.vpu.mp.common.foundation.data.JsonResultMessage;
import com.vpu.mp.common.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.common.foundation.excel.annotation.ExcelSheet;

import lombok.Data;

/**
 * @author: 王兵兵
 * @create: 2019-10-10 17:33
 **/
@ExcelSheet
@Data
public class VerifierExportVo {
    @ExcelColumn(columnName = JsonResultMessage.STORE_VERIFIER_LIST_USER_ID,columnIndex = 0)
    private Integer userId;

    @ExcelColumn(columnName = JsonResultMessage.STORE_VERIFIER_LIST_USERNAME,columnIndex = 1)
    private String username;

    @ExcelColumn(columnName = JsonResultMessage.STORE_VERIFIER_LIST_MOBILE,columnIndex = 2)
    private String mobile;

    /**
     * 核销订单数
     */
    @ExcelColumn(columnName = JsonResultMessage.STORE_VERIFIER_LIST_VERIFIER_ORDERS,columnIndex = 3)
    private Integer verifyOrders;
}
