package com.vpu.mp.service.shop.anchor;

import com.vpu.mp.common.pojo.shop.table.AnchorPointsDo;
import lombok.Getter;

import java.util.Objects;

/**
 * 锚点事件
 * @author 孔德成
 * @date 2020/8/28 17:06
 */
@Getter
public enum AnchorPointsEvent {

    /**
     * 下单(点击数量)
     */
    ORDER_SUB_COUNT("order_create","下单点击事件","count","订单模块",0),
    ORDER_CREATE_NUM("order_create","下单商品数量","num","订单模块",0),
    ORDER_CREATE_MONEY("order_create","下单支付金额","money","订单模块",0);

    /**
     * 埋点事件
     */
    private final String event;
    /**
     * 事件名称
     */
    private final String eventName;
    /**
     * 埋点类型（0前段/1后端/2定时）
     */
    private final Byte eventType;
    /**
     * 锚点类型
     */
    private final String key;
    /**
     * 模块
     */
    private final String module;




    AnchorPointsEvent(String event, String eventName, String key, String module, Integer eventType){
        this.event =event;
        this.eventName =eventName;
        this.key = key;
        this.eventType=eventType.byteValue();
        this.module = module;
    }


    public static AnchorPointsEvent getInstance(String event, String key) {
        for (AnchorPointsEvent pEvent : AnchorPointsEvent.values()) {
            if (Objects.equals(pEvent.event, event) && Objects.equals(pEvent.key, key)) {
                return pEvent;
            }
        }
        return null;
    }

    public AnchorPointsDo getDo(){
        AnchorPointsDo anchorPointsDo =new AnchorPointsDo();
        anchorPointsDo.setEvent(this.event);
        anchorPointsDo.setEventName(this.eventName);
        anchorPointsDo.setEventType(this.eventType);
        anchorPointsDo.setKey(this.key);
        anchorPointsDo.setModule(this.module);
        return anchorPointsDo;
    }


}
