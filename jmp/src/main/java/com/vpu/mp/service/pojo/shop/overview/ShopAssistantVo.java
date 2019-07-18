package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:liufei
 * @Date:2019/7/17
 * @Description:
 */
@Data
@Component
public class ShopAssistantVo {
    @Autowired
    private AssiDataShop dataShop;
    @Autowired
    private AssiDataGoods dataGoods;
    @Autowired
    private AssiDataOrder dataOrder;
    @Autowired
    private AssiDataMarket dataMarket;
    //待处理项总数
    private int totalPending;

    public void totalPendingIncr(){
        totalPending++;
    }

}
