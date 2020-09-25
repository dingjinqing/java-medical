package com.vpu.mp.service.saas.order;

import com.vpu.mp.common.pojo.main.table.ReturnOrderBakDo;
import com.vpu.mp.common.pojo.shop.table.ReturnOrderGoodsDo;
import com.vpu.mp.dao.shop.order.ReturnOrderDao;
import com.vpu.mp.dao.shop.order.ReturnOrderGoodsDao;
import com.vpu.mp.db.main.tables.records.ReturnOrderBakRecord;
import com.vpu.mp.db.main.tables.records.ReturnOrderGoodsBakRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.main.Tables.RETURN_ORDER_BAK;
import static com.vpu.mp.db.main.Tables.RETURN_ORDER_GOODS_BAK;

/**
 * @author 孔德成
 * @date 2020/8/20 18:54
 */
@Service
@Slf4j
public class SaasReturnOrderService extends MainBaseService {


    @Autowired
    private ReturnOrderDao returnOrderDao;
    @Autowired
    private ReturnOrderGoodsDao returnOrderGoodsDao;


    public void synOrderGoodsCreate(Timestamp beginTime, Timestamp endTime, Integer shopId) {
        List<ReturnOrderGoodsDo> createOrderGoodsList = returnOrderGoodsDao.listCreateOrderGoodsByYesterday(beginTime, endTime);
        createOrderGoodsList.forEach(orderInfoDo->{
            ReturnOrderGoodsBakRecord orderInfoBakRecord = databaseManager.mainDb().newRecord(RETURN_ORDER_GOODS_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            try {
                orderInfoBakRecord.insert();
            }catch (Exception e){
                log.info("退款订单同步失败{}",orderInfoBakRecord.getOrderSn());
            }
        });
    }

    public void synOrderGoodsUpdate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<ReturnOrderGoodsDo> updateOrderGoodsList = returnOrderGoodsDao.listUpdateOrderGoodsByYesterday(beginTime, endTime);
        updateOrderGoodsList.forEach(orderInfoDo->{
            ReturnOrderGoodsBakRecord orderInfoBakRecord = databaseManager.mainDb().newRecord(RETURN_ORDER_GOODS_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            try {
                orderInfoBakRecord.update();
            }catch (Exception e){
                log.info("退款订单同步失败{}",orderInfoBakRecord.getOrderSn());
            }
        });
    }

    public void synOrderCreate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<ReturnOrderBakDo> createOrderList = returnOrderDao.listCreateOrderByYesterday(beginTime, endTime);
        createOrderList.forEach(orderInfoDo->{
            ReturnOrderBakRecord orderInfoBakRecord = databaseManager.mainDb().newRecord(RETURN_ORDER_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            try {
                orderInfoBakRecord.insert();
            }catch (Exception e){
                log.info("退款订单同步失败{}",orderInfoBakRecord.getOrderSn());
            }
        });
    }

    public void synOrderUpdate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<ReturnOrderBakDo> updateOrderList = returnOrderDao.listUpdateOrderByYesterday(beginTime, endTime);
        updateOrderList.forEach(orderInfoDo->{
            ReturnOrderBakRecord orderInfoBakRecord = databaseManager.mainDb().newRecord(RETURN_ORDER_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            try {
                orderInfoBakRecord.update();
            }catch (Exception e){
                log.info("退款订单同步失败{}",orderInfoBakRecord.getOrderSn());
            }
        });
    }


}
