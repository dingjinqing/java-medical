package com.vpu.mp.service.pojo.shop.order.write.operate.prescription;

import com.vpu.mp.service.pojo.shop.order.write.operate.AbstractOrderOperateQueryParam;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 订单审核查询
 * @author 孔德成
 * @date 2020/7/9 10:24
 */
@Data
public class PrescriptionQueryParam extends AbstractOrderOperateQueryParam {

    @NotNull
    private String orderSn;
    @NotNull
    private Integer accountId;

}
