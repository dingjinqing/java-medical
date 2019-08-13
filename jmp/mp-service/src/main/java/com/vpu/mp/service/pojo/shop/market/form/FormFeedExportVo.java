package com.vpu.mp.service.pojo.shop.market.form;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author liufei
 * @date 2019/8/13
 * @description
 */
@ExcelSheet
@Data
public class FormFeedExportVo {
    @ExcelIgnore
    private Integer userId;
    @ExcelIgnore
    private Integer pageId;
    @ExcelIgnore
    private Integer submitId;
    @ExcelColumn(columnIndex = 0,columnName = "market.form.feedback.nickname")
    private String nickName;
    @ExcelColumn(columnIndex = 0,columnName = "market.form.feedback.mobile")
    private String mobile;
    @ExcelColumn(columnIndex = 0,columnName = "market.form.feedback.create_time")
    private Timestamp createTime;
}
