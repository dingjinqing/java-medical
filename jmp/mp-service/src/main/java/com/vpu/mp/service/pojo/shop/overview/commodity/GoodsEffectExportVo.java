package com.vpu.mp.service.pojo.shop.overview.commodity;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelDynamicColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.util.LinkedHashMap;

/**
 * @author liufei
 * @date 2/18/2020
 */
@ExcelSheet
@Data
public class GoodsEffectExportVo {
    @ExcelColumn(columnName = JsonResultMessage.GOODS_RANKING_GOODS_NAME, columnIndex = 0)
    private String goodsName;

    @ExcelDynamicColumn
    private LinkedHashMap<String, Object> dynamicValue;
}
