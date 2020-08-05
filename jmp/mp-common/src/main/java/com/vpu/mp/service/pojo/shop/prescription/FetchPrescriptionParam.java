package com.vpu.mp.service.pojo.shop.prescription;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @Description 从hits系统同步处方信息
 * @Author zhaoxiaodong
 * @create 2020-07-16 09:00
 */

@Data
@NoArgsConstructor
public class FetchPrescriptionParam {

    /**
     * 患者姓名
     */
    @NonNull
    private String name;
    /**
     * 患者电话
     */
    private String mobile;
    /**
     * 患者身份证号
     */
    private String identityCode;

    /**
     * 增量查询时间
     */
    private Long startTime;
}
