package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.db.shop.tables.records.*;
import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsGradePrd;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelAndCouple;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.shop.goods.sort.Sort;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.market.seckill.SecKillProductVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.saas.categroy.SysCatServiceHelper;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import com.vpu.mp.service.shop.goods.*;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.market.bargain.BargainService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import com.vpu.mp.service.shop.market.presale.PreSaleService;
import com.vpu.mp.service.shop.market.reduceprice.ReducePriceService;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GROUP_BUY_PRODUCT_DEFINE;
import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;

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
        commitEdGoodsIndex(esAssemblyDataService.assemblyEsGoods(goodsId, shopId));
    }

    /**
     * 单个删除es数据（删除调用）
     * @param goodsId
     * @param shopId
     */
    public void deleteEsGoods(Integer goodsId,Integer shopId){
        try {
            esManager.deleteIndexById(EsGoodsConstant.INDEX_NAME,goodsId.toString()+shopId);
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
            esManager.deleteIndexById(EsGoodsConstant.INDEX_NAME,list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void commitEdGoodsIndex(EsGoods esGoods){
        try{
            esManager.createDocuments(esManager.assemblyRequest(EsGoodsConstant.INDEX_NAME,esGoods));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void batchCommitEsGoodsIndex(List<EsGoods> list){
        BulkRequest requests = new BulkRequest();
        for( EsGoods goods: list ){
            requests.add(esManager.assemblyRequest(EsGoodsConstant.INDEX_NAME,goods));
        }
        try {
            esManager.batchDocuments(requests);
        } catch (IOException e) {
            log.error("批量建立索引失败");
        }
    }
}
