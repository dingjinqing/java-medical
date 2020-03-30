package com.vpu.mp.service.pojo.wxapp.order.term;

import com.vpu.mp.service.shop.config.TradeService;
import lombok.Getter;
import lombok.Setter;

/**
 * 服务条款
 * @author 王帅
 */
@Getter
@Setter
public class OrderTerm {
    private Byte serviceTerms;
    private String serviceName;
    private Byte serviceChoose;

    public void init(TradeService trade){
        this.serviceTerms = trade.getServiceTerms();
        this.serviceName = trade.getServiceName();
        this.serviceChoose = trade.getServiceChoose();
    }
}
