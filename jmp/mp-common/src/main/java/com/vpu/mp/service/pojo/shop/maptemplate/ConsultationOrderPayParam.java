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
public class ConsultationOrderPayParam {
    private String patientData;
    private String diseaseDetail;
    private String createTime;
    private String remark;
    private List<Integer> userIds;
}
