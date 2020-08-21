package com.vpu.mp.service.saas.order;

import com.vpu.mp.common.pojo.shop.table.OrderInfoDo;
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
import java.util.ArrayList;
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
        List<ReturnOrderBakRecord> createOrderGoodsByYesterday =new ArrayList<>();
        createOrderGoodsList.forEach(orderInfoDo->{
            ReturnOrderBakRecord orderInfoBakRecord = db().newRecord(RETURN_ORDER_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            createOrderGoodsByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchInsert(createOrderGoodsByYesterday).execute();
    }

    public void synOrderGoodsUpdate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<ReturnOrderGoodsDo> updateOrderGoodsList = returnOrderDao.listUpdateOrderGoodsByYesterday(beginTime, endTime);
        List<ReturnOrderGoodsBakRecord> updateOrderGoodsByYesterday =new ArrayList<>();
        updateOrderGoodsList.forEach(orderInfoDo->{
            ReturnOrderGoodsBakRecord orderInfoBakRecord = db().newRecord(RETURN_ORDER_GOODS_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            updateOrderGoodsByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchUpdate(updateOrderGoodsByYesterday).execute();
    }

    public void synOrderCreate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<OrderInfoDo> createOrderList = returnOrderDao.listCreateOrderByYesterday(beginTime, endTime);
        List<ReturnOrderBakRecord> createOrderByYesterday =new ArrayList<>();
        createOrderList.forEach(orderInfoDo->{
            ReturnOrderBakRecord orderInfoBakRecord = db().newRecord(RETURN_ORDER_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            createOrderByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchInsert(createOrderByYesterday).execute();
    }

    public void synOrderUpdate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<OrderInfoDo> updateOrderList = returnOrderDao.listUpdateOrderByYesterday(beginTime, endTime);
        List<ReturnOrderBakRecord> updateOrderByYesterday =new ArrayList<>();
        updateOrderList.forEach(orderInfoDo->{
            ReturnOrderBakRecord orderInfoBakRecord = db().newRecord(RETURN_ORDER_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            updateOrderByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchUpdate(updateOrderByYesterday).execute();
    }


}
