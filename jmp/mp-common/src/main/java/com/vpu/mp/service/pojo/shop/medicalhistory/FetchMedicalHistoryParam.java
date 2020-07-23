package com.vpu.mp.service.pojo.shop.medicalhistory;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @Description
 * @Author zhaoxiaodong
 * @create 2020-07-20 17:32
 */
@Data
@NoArgsConstructor
public class FetchMedicalHistoryParam {

    /**
     * 患者姓名
     */
    @NonNull
    private String patientName;
    /**
     * 患者电话
     */
    @NonNull
    private String mobile;
    /**
     * 患者医院唯一编码
     */
    private String patientCode;
    /**
     * 患者身份证号
     */
    private String identityCode;

    /**
     * 增量查询时间
     */
    private Long startTime;

}