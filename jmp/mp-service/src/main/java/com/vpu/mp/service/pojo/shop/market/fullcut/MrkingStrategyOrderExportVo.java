package com.vpu.mp.service.pojo.shop.market.fullcut;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.util.List;

/**
 * @author: 王兵兵
 * @create: 2020-05-11 13:41
 **/
@ExcelSheet
@Data
public class MrkingStrategyOrderExportVo {

    @ExcelColumn(columnName = JsonResultMessage.MRKING_STRATEGY_ORDER_LIST_ORDER_SN, columnIndex = 0)
    private String orderSn;

    /**
     * 行信息
     */
    @ExcelColumn(columnName = JsonResultMessage.MRKING_STRATEGY_ORDER_LIST_GOODS_NAME, columnIndex = 1)
    private List<MrkingStrategyOrderGoodsExportVo> goods;

    @ExcelColumn(columnName = JsonResultMessage.MRKING_STRATEGY_ORDER_LIST_USERNAME, columnIndex = 6)
    private String username;

    @ExcelColumn(columnName = JsonResultMessage.MRKING_STRATEGY_ORDER_LIST_ORDER_STATUS, columnIndex = 7)
    private String orderStatus;
}
