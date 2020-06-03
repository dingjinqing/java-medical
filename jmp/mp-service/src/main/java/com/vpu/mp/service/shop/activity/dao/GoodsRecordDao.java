package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.order.record.GoodsOrderRecordSmallVo;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 购买记录
 * @author 孔德成
 * @date 2020/3/31
 */
@Component
public class GoodsRecordDao extends ShopBaseService {

    @Autowired
    private OrderGoodsService orderGoodsService;

    /**
     * 最进的物件商品购买记录
     * @param goodsId
     * @return
     */
    public List<GoodsOrderRecordSmallVo> getGoodsRecord(Integer goodsId){
        return orderGoodsService.getGoodsRecord(goodsId);
    }
}
