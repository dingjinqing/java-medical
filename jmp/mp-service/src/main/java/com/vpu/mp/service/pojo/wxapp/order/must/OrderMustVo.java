package com.vpu.mp.service.pojo.wxapp.order.must;

import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.shop.config.TradeService;
import lombok.Getter;
import lombok.Setter;

/**
 * 下单必填信息
 * @author 王帅
 */
@Getter
@Setter
public class OrderMustVo {
    private Byte isShow;
    private Byte orderRealName;
    private Byte orderCid;
    private Byte consigneeRealName;
    private Byte consigneeCid;
    private Byte custom;
    private String customTitle;

    /**
     * 使用trade初始化
     * @param trade
     */
    public void init(TradeService trade){
        this.orderRealName = trade.getOrderRealName();
        this.orderCid = trade.getOrderCid();
        this.consigneeRealName = trade.getConsigneeRealName();
        this.consigneeCid = trade.getConsigneeCid();
        this.custom = trade.getCustom();
        this.customTitle = trade.getCustomTitle();
    }

    /**
     * 校验
     * @return
     */
    public Byte isCheck(){
        if(isShow == null){
            if((orderRealName + orderCid + consigneeRealName + consigneeCid + custom) > 0){
                isShow = OrderConstant.YES;
            }else{
                isShow = OrderConstant.NO;
            }
        }
        return isShow;
    }

    /**
     * 展示
     * @return
     */
    public OrderMustVo show(){
        isShow = OrderConstant.YES;
        return this;
    }

    public OrderMustVo hide(){
        isShow = OrderConstant.NO;
        return this;
    }
}
