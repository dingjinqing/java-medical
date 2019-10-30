package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;

/**
 * author liufei
 * date 2019/7/17
 */
@Data
public class ShopAssistantVo {
    private AssiDataShop dataShop;
    private AssiDataGoods dataGoods;
    private AssiDataOrder dataOrder;
    private AssiDataMarket dataMarket;
    /** 待处理项总数 */
    private int totalPending;

    public void totalPendingIncr(){
        totalPending++;
    }

}
