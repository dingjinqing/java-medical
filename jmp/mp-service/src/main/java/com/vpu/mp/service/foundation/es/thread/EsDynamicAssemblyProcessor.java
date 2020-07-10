package com.vpu.mp.service.foundation.es.thread;

import com.google.common.collect.Lists;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.service.foundation.es.pojo.goods.EsGoods;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class EsDynamicAssemblyProcessor extends MainBaseService {


    public List<EsGoods> assemblyData(ShopApplication shop, List<Integer> goodsIds, Integer shopId){
        Map<Integer, GoodsRecord> goodsMap = shop.goods.getGoodsByIds(goodsIds);
        List<EsGoods> result = Lists.newArrayList();
        for( Map.Entry<Integer,GoodsRecord> entry : goodsMap.entrySet() ){
            EsGoods goods = new EsGoods();
            goods.setGoodsId(entry.getKey());
            goods.setShopId(shopId);
            dynamicAssemblyData(goods);
            result.add(goods);
        }
        return result;
    }

    /**
     *  动态设置es需要建索引的字段
     * @param goods 商品
     */
    private void dynamicAssemblyData(EsGoods goods){
    }

}
