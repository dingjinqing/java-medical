package com.vpu.mp.service.pojo.wxapp.order.address;

import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsBaseCheckInfo;
import lombok.Data;

import java.util.List;

/**
 * @author 赵晓东
 * @description
 * @create 2020-08-26 14:46
 **/
@Data
public class OrderAddressParam {

    private String lat;

    private String lng;

    private List<StoreGoodsBaseCheckInfo> storeGoodsBaseCheckInfoList;

    private Integer deliveryType;
}
