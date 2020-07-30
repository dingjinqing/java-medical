package com.vpu.mp.service.pojo.shop.order.goods.param;

import lombok.Data;

import java.util.List;

/**
 * @author yangpengcheng
 */
@Data
public class MedicalOrderExternalRequestParam {
    private Integer status;
    private String orderSn;
    private List<GoodsItem> goodsItemList;
}
