package com.vpu.mp.service.shop.order.atomic;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import com.vpu.mp.service.foundation.util.lock.annotation.operation.AddRedisLocks;
import com.vpu.mp.service.foundation.util.lock.annotation.operation.ReleaseRedisLocks;
import com.vpu.mp.service.pojo.shop.goods.goods.BatchUpdateGoodsNumAndSaleNumForOrderParam;
import com.vpu.mp.service.pojo.shop.goods.goods.BatchUpdateGoodsNumAndSaleNumForOrderParam.ProductNumInfo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单库存、销量更新规则
 * @author 王帅
 */
@Component
@Slf4j
public class AtomicOperation extends ShopBaseService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSpecProductService goodsSpecProduct;

    /**
     * 普通商品库存更新
     * @param order 订单
     * @param goodsBo 商品
     * @param limit 超卖限制 true 不允许超卖，false 允许超卖
     * @throws MpException
     */
    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    public void updateStockAndSalesByLock(OrderInfoRecord order, @RedisLockKeys List<OrderGoodsBo> goodsBo, boolean limit) throws MpException {
        updateStockandSales(order, goodsBo, limit);
    }



    @AddRedisLocks(redisLock = @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK))
    public void addLock(@RedisLockKeys List<OrderGoodsBo> goodsBo){
        log.info("atomicOperation addLock end");
    }

    @ReleaseRedisLocks
    public void releaseLocks(){
        log.info("atomicOperation releaseLocks end");
    }

    public void updateStockandSales(OrderInfoRecord order, List<OrderGoodsBo> goodsBo, boolean limit) throws MpException {
        log.info("AtomicOperation.updateStockAndSales订单库存销量更新start,订单号{},商品{}", order.getOrderId(), goodsBo);
        if(Boolean.FALSE) {
            //todo 营销
        }else {

        }
        //TODO 第三方对接erp
        List<Integer> goodsIds = goodsBo.stream().map(OrderGoodsBo::getGoodsId).distinct().collect(Collectors.toList());
        List<Integer> proIds = goodsBo.stream().map(OrderGoodsBo::getProductId).collect(Collectors.toList());
        //商品map
        Map<Integer, GoodsRecord> goodsRecord = goodsService.getGoodsByIds(goodsIds);
        //规格map
        Map<Integer, GoodsSpecProductRecord> productRecord = goodsSpecProduct.selectSpecByProIds(proIds);
        //商品更新map
        Map<Integer , BatchUpdateGoodsNumAndSaleNumForOrderParam> updateGoodsMap = new HashMap<>(goodsIds.size());
        for (OrderGoodsBo goodsRow : goodsBo) {
            //购买数量
            int num = goodsRow.getGoodsNumber();
            //规格
            GoodsSpecProductRecord product = productRecord.get(goodsRow.getProductId());
            //商品
            GoodsRecord goods = goodsRecord.get(goodsRow.getGoodsId());
            //update goods
            BatchUpdateGoodsNumAndSaleNumForOrderParam updateGoods = updateGoodsMap.get(goodsRow.getGoodsId());
            if(updateGoods == null) {
                updateGoods = BatchUpdateGoodsNumAndSaleNumForOrderParam.builder().goodsId(goodsRow.getGoodsId()).build();
                updateGoodsMap.put(goodsRow.getGoodsId(), updateGoods);
            }
            if(product == null || goods == null){
                //商品或规格row为null
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NO_EXIST , null, order.getOrderSn());
            }
            //商品库存
            int goodsStock = goods.getGoodsNumber() - num;
            //商品销量
            int goodsSales = goods.getGoodsSaleNum() + num;
            //规格库存
            int productStock = product.getPrdNumber() - num;
            log.error("规格原库存；{}，扣减库存：{}", product.getPrdNumber(), productStock);
            if(product.getPrdNumber() < num) {
                //库存不足
                log.error("库存不足订单超卖,订单号:{},规格id:{};" +
                        "原商品库存:{},原商品销量:{},原规格库存:{};" +
                        "此次购买数量:{}",
                    order.getOrderSn(), product.getPrdId(),
                    goods.getGoodsNumber(), goods.getGoodsSaleNum(), product.getPrdNumber(),
                    num
                );
                if(limit) {
                    throw new MpException(JsonResultCode.CODE_ORDER_GOODS_LOW_STOCK);
                }
                //不允许出现负值
                if(goodsStock < 0) {
                    goodsStock = 0;
                }
                if(productStock < 0) {
                    productStock = 0;
                }
            }
            //内存修改
            goods.setGoodsNumber(goodsStock);
            goods.setGoodsSaleNum(goodsSales);
            product.setPrdNumber(productStock);
            //updateGoods 赋值
            updateGoods.setGoodsNum(goodsStock);
            updateGoods.setSaleNum(goodsSales);
            updateGoods.addProductsInfo(ProductNumInfo.builder().prdId(goodsRow.getProductId()).prdNum(productStock).build());
        }
        //商品更新list
        List<BatchUpdateGoodsNumAndSaleNumForOrderParam> updateGoods = new ArrayList<>(updateGoodsMap.values());
        //商品库存更新
        goodsService.batchUpdateGoodsNumsAndSaleNumsForOrder(updateGoods);
        //
        //TODO 活动库存更新
        log.info("AtomicOperation.updateStockAndSales订单库存销量更新end");
    }
}
