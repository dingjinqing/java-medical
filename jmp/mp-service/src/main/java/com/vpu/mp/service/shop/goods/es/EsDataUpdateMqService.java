package com.vpu.mp.service.shop.goods.es;


import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.jedis.data.DBOperating;
import com.vpu.mp.service.foundation.jedis.data.label.MqEsGoodsLabel;
import com.vpu.mp.service.foundation.mq.RabbitmqSendService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.es.EsTaskParam;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * ElasticSearch External Service
 * @author luguangyao
 * @date 2019/10/23
 *
*/
@Service
public class EsDataUpdateMqService extends BaseShopConfigService {

    @Autowired
    private RabbitmqSendService rabbitmqSendService;




    /**
     *  update EsGoods data by goods id
     * @param goodsIds goodsIdList
     * @param shopId shop id
     */
    public void addEsGoodsIndex(List<Integer> goodsIds,Integer shopId){
        EsTaskParam param = new EsTaskParam();
        param.setIdList(goodsIds);
        param.setShopId(shopId);
        rabbitmqSendService.sendMessage(RabbitConfig.EXCHANGE_ES,RabbitConfig.BINDING_ES_GOODS_KEY,
            Util.toJson(param),param.getClass().getName());

    }
    /**
     * update {@link EsGoodsLabel} data by labelId/goodsId
     * @param labelIds labelIds
     * @param shopId shop id
     * @param goodsIds goodsIds
     */
    public void updateGoodsLabelByLabelId(@NotNull Integer shopId, DBOperating operating,
                                          List<Integer> goodsIds, List<Integer> labelIds){
        MqEsGoodsLabel param = new MqEsGoodsLabel();
        if(goodsIds != null && !goodsIds.isEmpty()){
            param.setGoodsIds(goodsIds);
        }
        if( goodsIds != null && !goodsIds.isEmpty() ){
            param.setLabelIds(labelIds);
        }
        param.setOperating(operating);
        param.setShopId(shopId);
        rabbitmqSendService.sendMessage(RabbitConfig.EXCHANGE_ES,RabbitConfig.BINDING_EXCHANGE_ES_GOODS_LABEL_KEY,
            Util.toJson(param),param.getClass().getName());

    }

}
