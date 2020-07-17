package com.vpu.mp.service.pojo.shop.medical.order.param;

import lombok.Data;

import java.util.List;

@Data
public class MedicalOrderExternalRequestParam {
    private Integer status;
    private String orderSn;
    private List<GoodsItem> list;
}
