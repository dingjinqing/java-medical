package com.vpu.mp.service.pojo.shop.overview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author liufei
 * date 2019/7/17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
