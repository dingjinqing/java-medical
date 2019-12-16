package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * EsGoods相关操作
 * @author 卢光耀
 * @date 2019/10/9 10:23 上午
*/
@Slf4j
@Service
public class EsGoodsCreateService extends BaseShopConfigService {

    @Autowired
    private EsManager esManager;
    @Autowired
    private EsAssemblyDataService esAssemblyDataService;

    /**
     * 批量更新es数据（新增/修改调用）
     * @param goodsIds
     * @param shopId
     */
    public void batchCreateEsGoodsIndex( List<Integer> goodsIds,Integer shopId){
        List<EsGoods> esGoodsList = esAssemblyDataService.assemblyEsGoods(goodsIds, shopId);
        deleteEsGoods(goodsIds,shopId);
        batchCommitEsGoodsIndex(esGoodsList);
    }

    /**
     * 单个更新es数据（新增/修改调用）
     * @param goodsId 商品id
     */
    public void createEsGoodsIndex(Integer goodsId,Integer shopId){
        deleteEsGoods(goodsId,shopId);
        batchCommitEsGoodsIndex(esAssemblyDataService.assemblyEsGoods(Collections.singletonList(goodsId), shopId));
    }

    /**
     * 单个删除es数据（删除调用）
     * @param goodsId
     * @param shopId
     */
    public void deleteEsGoods(Integer goodsId,Integer shopId){
        try {
            esManager.deleteIndexById(EsGoodsConstant.GOODS_INDEX_NAME,goodsId.toString()+shopId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 批量删除es数据（删除调用）
     * @param goodsIds
     * @param shopId
     */
    public void deleteEsGoods(List<Integer> goodsIds,Integer shopId){
        try {
            List<String> list = goodsIds.stream().map(x->shopId.toString()+x).collect(Collectors.toList());
            esManager.deleteIndexById(EsGoodsConstant.GOODS_INDEX_NAME,list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void commitEdGoodsIndex(EsGoods esGoods){
        try{
            esManager.createDocuments(esManager.assemblyRequest(EsGoodsConstant.GOODS_INDEX_NAME,esGoods));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void batchCommitEsGoodsIndex(List<EsGoods> list){
        BulkRequest requests = new BulkRequest();
        for( EsGoods goods: list ){
            requests.add(esManager.assemblyRequest(EsGoodsConstant.GOODS_INDEX_NAME,goods));
        }
        try {
            esManager.batchDocuments(requests);
        } catch (IOException e) {
            log.error("批量建立索引失败");
        }
    }
}
