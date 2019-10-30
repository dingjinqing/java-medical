package com.vpu.mp.service.shop.goods.es;


import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.mq.RabbitmqSendService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.es.EsTaskParam;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * es对外service
 * @author 卢光耀
 * @date 2019/10/23 1:55 下午
 *
*/
@Service
public class EsGoodsService extends BaseShopConfigService {

    @Autowired
    private RabbitmqSendService rabbitmqSendService;

    /**
     *  更新索引接口
     * @param goodsIds goodsIdList
     * @param shopId 店铺Id
     */
    public void addEsGoodsIndex(List<Integer> goodsIds,Integer shopId){
        EsTaskParam param = new EsTaskParam();
        param.setGoodsIdList(goodsIds);
        param.setShopId(shopId);
        rabbitmqSendService.sendMessage(RabbitConfig.EXCHANGE_ES,RabbitConfig.BINDING_ES_GOODS_KEY, Util.toJson(param),param.getClass().getName());

    }
}
