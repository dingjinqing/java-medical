package com.vpu.mp.service.saas.order;

import com.vpu.mp.common.pojo.shop.table.OrderGoodsDo;
import com.vpu.mp.common.pojo.shop.table.OrderInfoDo;
import com.vpu.mp.dao.shop.order.OrderGoodsDao;
import com.vpu.mp.dao.shop.order.OrderInfoDao;
import com.vpu.mp.db.main.tables.records.OrderInfoBakRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.main.Tables.ORDER_INFO_BAK;

/**
 * @author luguangyao
 */
@Service
@Slf4j
public class SaasOrderService extends MainBaseService {

    @Autowired
    private OrderGoodsDao orderGoodsDao;
    @Autowired
    private OrderInfoDao orderInfoDao;

    public void synOrderGoodsCreate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<OrderGoodsDo> createOrderGoodsList = orderGoodsDao.listCreateOrderGoodsByYesterday(beginTime, endTime);
        List<OrderInfoBakRecord> createOrderGoodsByYesterday =new ArrayList<>();
        createOrderGoodsList.forEach(orderInfoDo->{
            OrderInfoBakRecord orderInfoBakRecord = db().newRecord(ORDER_INFO_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            createOrderGoodsByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchInsert(createOrderGoodsByYesterday).execute();
    }

    public void synOrderGoodsUpdate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<OrderGoodsDo> updateOrderGoodsList = orderGoodsDao.listUpdateOrderGoodsByYesterday(beginTime, endTime);
        List<OrderInfoBakRecord> updateOrderGoodsByYesterday =new ArrayList<>();
        updateOrderGoodsList.forEach(orderInfoDo->{
            OrderInfoBakRecord orderInfoBakRecord = db().newRecord(ORDER_INFO_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            updateOrderGoodsByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchUpdate(updateOrderGoodsByYesterday).execute();
    }

    public void synOrderCreate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<OrderInfoDo> createOrderList = orderInfoDao.listCreateOrderByYesterday(beginTime, endTime);
        List<OrderInfoBakRecord> createOrderByYesterday =new ArrayList<>();
        createOrderList.forEach(orderInfoDo->{
            OrderInfoBakRecord orderInfoBakRecord = db().newRecord(ORDER_INFO_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            createOrderByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchInsert(createOrderByYesterday).execute();
    }

    public void synOrderUpdate(Timestamp beginTime, Timestamp endTime,Integer shopId) {
        List<OrderInfoBakRecord> updateOrderByYesterday =new ArrayList<>();
        List<OrderInfoDo> updateOrderList = orderInfoDao.listUpdateOrderByYesterday(beginTime, endTime);
        updateOrderList.forEach(orderInfoDo->{
            OrderInfoBakRecord orderInfoBakRecord = db().newRecord(ORDER_INFO_BAK, orderInfoDo);
            orderInfoBakRecord.setShopId(shopId);
            updateOrderByYesterday.add(orderInfoBakRecord);
        });
        databaseManager.mainDb().batchUpdate(updateOrderByYesterday).execute();
    }


}
