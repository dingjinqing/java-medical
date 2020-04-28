package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.shop.member.AddressService;
import com.vpu.mp.service.shop.order.action.CreateService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 配送信息
 * @author 孔德成
 * @date 2020/4/28
 */
@Getter
@Setter
public class ShippingProcessor implements Processor,GoodsDetailProcessor {
    @Autowired
    private AddressService addressService;
    @Autowired
    private CreateService createService;
    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_GENERAL;
    }


    /**
     * 1 已售罄位置 未设置收货地址 显示当前位置
     * 2 未设置位置 未设置地址 显示不可配送
     * 3 设置地址 显示地址
     *
     *
     * @param capsule  商品详情对象{@link com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo}
     * @param param
     */
    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        createService.getDefaultAddress(param.getUserId(),null);

    }
}
