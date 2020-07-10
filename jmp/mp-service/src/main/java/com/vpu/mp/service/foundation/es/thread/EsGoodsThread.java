package com.vpu.mp.service.foundation.es.thread;

import com.google.common.collect.Lists;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.service.foundation.es.pojo.goods.EsGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

@Component
@Slf4j
public class EsGoodsThread  implements Callable<Boolean> {

    private final SaasApplication saas;



    private Integer shopId;

    private ShopApplication shop;

    private String indexName;

    public EsGoodsThread(SaasApplication saas) {
        this.saas = saas;
    }

    public void init(Integer shopId,String indexName){
        this.shopId = shopId;
        this.indexName = indexName;
    }

    @Override
    public Boolean call() throws Exception {
        boolean result = Boolean.TRUE;
        shop = saas.getShopApp(shopId);
        List<Integer> allGoodIds = shop.goods.getAllGoodsId();
        log.info("【ES索引切换】---店铺【{}】需要建立【{}】个商品",shopId,allGoodIds.size());
        Integer times = getTimes(allGoodIds.size());
        int index = 0;
        for( int i = 0; i < times; i++ ){
            List<EsGoods> esGoodsList = Lists.newArrayList();
            List<Integer> goodsIds;
            if( allGoodIds.size() > index+400 ){
                goodsIds = allGoodIds.subList(index,index+400);
            }else{
                goodsIds = allGoodIds.subList(index,allGoodIds.size());
            }
            index+=400;
            try {
                esGoodsList = saas.esMappingUpdateService.processor.assemblyData(shop,goodsIds,shopId);
            }catch (Exception e){
                result = Boolean.FALSE;
            }
            if( !esGoodsList.isEmpty()){
                shop.esGoodsCreateService.batchCreateEsGoodsIndex(esGoodsList,indexName);
            }

        }
        return result;
    }
    private Integer getTimes(Integer allSize){
        Integer times = allSize/400;
        if( allSize%400 != 0 ){
            times++;
        }
        return times;
    }

}
