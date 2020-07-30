package com.vpu.mp.service.pojo.shop.order.write.operate.prescription.audit;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2020/7/29 13:56
 */
@Data
public class OrderGoodsSimpleAuditVo {
    /**
     * 订单商品id
     */
    private Integer recId;
    /**
     * 关联处方号
     */
    private String prescriptionOldCode;
    /**
     * 处方号
     */
    private String prescriptionCode;
    /**
     * 处方审核类型
     */
    private Byte medicalAuditType;
    /**
     * 处方审核状态
     */
    private Byte medicalAuditStatus;
}
