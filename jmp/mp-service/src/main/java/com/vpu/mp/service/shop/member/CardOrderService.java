package com.vpu.mp.service.shop.member;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.buy.CardBuyClearingParam;
import com.vpu.mp.service.pojo.shop.member.buy.CardBuyClearingVo;
import org.springframework.stereotype.Service;

/**
 * 会员卡订单
 * @author 孔德成
 * @date 2020/4/11
 */
@Service
public class CardOrderService extends ShopBaseService {


    /**
     * 检查是否可以下单
     * @param cardBuyVo
     */
    public void checkIsCanOrder(CardBuyClearingVo cardBuyVo){
        if (cardBuyVo.getInvoiceSwitch().equals(2)){

        }


    }

}
