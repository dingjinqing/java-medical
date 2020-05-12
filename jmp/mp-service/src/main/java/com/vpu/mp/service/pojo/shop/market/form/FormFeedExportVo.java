package com.vpu.mp.service.pojo.shop.market.form;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author liufei
 * @date 2019/8/13
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
    @ExcelColumn(columnIndex = 0, columnName = JsonResultMessage.FORM_FEED_NICKNAME)
    private String nickName;
    @ExcelColumn(columnIndex = 1, columnName = JsonResultMessage.FORM_FEED_MOBILE)
    private String mobile;
    @ExcelColumn(columnIndex = 2, columnName = JsonResultMessage.FORM_FEED_CREATE_TIME)
    private Timestamp createTime;
}
