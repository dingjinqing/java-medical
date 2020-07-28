package com.vpu.mp.service.pojo.wxapp.order.medical;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2020/7/28 14:25
 */
@Data
public class OrderMedicalHistoryBo {
    /**
     * 患者名称
     */
    private String patientName;
    /**
     * 患者id
     */
    private Integer patientId;
    /**
     * 性别
     */
    private Byte sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 患者主诉
     */
    private String patientComplain;
    /**
     * 历史诊断
     */
    private String diseaseHistory;
    /**
     * 图片地址
     */
    private String imagesList;
    /**
     * 订单id
     */
    private Integer orderId;

}
