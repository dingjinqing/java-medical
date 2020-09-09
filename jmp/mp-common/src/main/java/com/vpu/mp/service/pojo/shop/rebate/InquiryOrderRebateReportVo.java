package com.vpu.mp.service.pojo.shop.rebate;

import com.vpu.mp.common.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.common.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

/**
 * @author yangpengcheng
 * @date 2020/9/9
 **/
@Data
@ExcelSheet
public class InquiryOrderRebateReportVo {
    @ExcelIgnore
    public static final String EXPORT_FILE_NAME="咨询返利导出-";

    private String doctorName;
}
