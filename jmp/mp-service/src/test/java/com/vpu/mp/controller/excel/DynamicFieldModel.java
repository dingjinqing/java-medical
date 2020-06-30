package com.vpu.mp.controller.excel;

import lombok.Data;

import java.util.Map;

import com.vpu.mp.common.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.common.foundation.excel.annotation.ExcelDynamicColumn;
import com.vpu.mp.common.foundation.excel.annotation.ExcelSheet;

/**
 * @author 李晓冰
 * @date 2020年02月18日
 */
@ExcelSheet
@Data
public class DynamicFieldModel {
    @ExcelColumn(columnName = "excel.goods.name",columnIndex = 0)
    private String goodsName;

    @ExcelDynamicColumn
    private Map<String,Object> dynamicValue;
}
