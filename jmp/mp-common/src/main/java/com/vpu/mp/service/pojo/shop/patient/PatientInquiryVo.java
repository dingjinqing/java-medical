package com.vpu.mp.service.pojo.shop.patient;

import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import lombok.Data;

/**
 * @author 赵晓东
 * @description
 * @create 2020-10-15 13:14
 **/
@Data
public class PatientInquiryVo extends InquiryOrderDo {
    /**
     * 咨询记录表跳转字段
     */
    private String doctorCode;

}
