package com.vpu.mp.service.pojo.shop.order.goods;

import com.vpu.mp.common.pojo.shop.table.OrderMedicalHistoryDo;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsMedicalOneInfoVo;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientDetailVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author yangpengcheng
 * @date 2020/7/24
 **/
@Data
public class OrderGoodsMedicalVo {
    private Integer orderId;
    private String orderSn;
    private Timestamp createTime;
    private UserPatientDetailVo patient;
    private OrderMedicalHistoryDo medicalHistory;
    private List<GoodsMedicalOneInfoVo> goodsMedicalOneInfoVoList;
}
