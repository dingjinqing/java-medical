package com.vpu.mp.service.pojo.shop.order.write.operate.prescription;

import com.vpu.mp.service.pojo.shop.order.write.operate.AbstractOrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionDrugVo;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单开方
 * @author yangpengcheng
 * @date 2020/7/24
 **/
@Data
public class PrescriptionMakeParam extends AbstractOrderOperateQueryParam {
    private Integer orderId;
    private Integer patientId;
    private Integer doctorId;
    private String departmentCode;
    private String departmentName;
    private String diagnosisName;
    private String doctorAdvice;
    private Byte auditStatus;
    private List<PrescriptionDrugVo> goodsList=new ArrayList<>();
}
