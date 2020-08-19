package com.vpu.mp.service.pojo.shop.maptemplate;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author chenjie
 * @date 2020年08月18日
 */
@Data
@Builder
public class ConsultationSuccessParam {
    public static final String REMARK = "您的医生已接诊，请查看";
    private String patientName;
    private String diseaseDetail;
    private String doctorName;
    private String departmentName;
    @Builder.Default
    private String remark=REMARK;
    private List<Integer> userIds;
}
